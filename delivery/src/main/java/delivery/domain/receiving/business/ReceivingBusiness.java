package delivery.domain.receiving.business;

import db.domain.goods.GoodsEntity;
import db.domain.goods.enums.GoodsStatus;
import db.domain.receiving.ReceivingEntity;
import db.domain.receiving.enums.ReceivingStatus;
import db.domain.users.UserEntity;
import delivery.common.error.GoodsErrorCode;
import delivery.common.error.ReceivingErrorCode;
import delivery.common.error.ShippingErrorCode;
import delivery.common.exception.goods.GoodsNotInReceivingException;
import delivery.common.exception.goods.GoodsNotInShippingIngException;
import delivery.common.exception.receiving.ReceivingNotInConfirmationException;
import delivery.common.utils.datetime.DateTimeUtils;
import delivery.common.utils.datetime.DateTimeUtils.RequestDateTime;
import delivery.domain.goods.converter.GoodsConverter;
import delivery.domain.goods.service.GoodsService;
import delivery.domain.receiving.controller.model.ReceivingResponse;
import delivery.domain.receiving.controller.model.ReceivingResponseList;
import delivery.domain.receiving.converter.ReceivingConverter;
import delivery.domain.receiving.service.ReceivingService;
import delivery.domain.users.converter.UserConverter;
import delivery.domain.users.service.UserService;
import global.annotation.Business;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Business
@RequiredArgsConstructor
public class ReceivingBusiness {

    private final ReceivingService receivingService;
    private final ReceivingConverter receivingConverter;
    private final UserService userService;
    private final UserConverter userConverter;
    private final GoodsService goodsService;
    private final GoodsConverter goodsConverter;

    public ReceivingResponseList getReservationList() {

        List<ReceivingEntity> receivingEntityList = receivingService.getRequestList();

        return getResponseList(receivingEntityList);
    }

    public ReceivingResponse getReservation(Long requestId) {

        ReceivingEntity receivingEntity = receivingService.getRequestBy(requestId);

        ReceivingResponse response = setGoodsIdAndUserNameReceivingResponse(receivingEntity);

        return response;
    }

    public ReceivingResponse reservationConfirmed(Long requestId) {

        ReceivingEntity receivingEntity = receivingService.reservationConfirmed(requestId);

        ReceivingResponse receivingResponse = setGoodsIdAndUserNameReceivingResponse(
            receivingEntity);

        return receivingResponse;
    }

    private ReceivingResponse setGoodsIdAndUserNameReceivingResponse(
        ReceivingEntity receivingEntity) {

        List<Long> goodsIdList = goodsService.getReceivingGoodsList(receivingEntity.getId())
            .stream().map(goodsEntity -> {
                return goodsEntity.getId();
            }).toList();

        ReceivingResponse response = receivingConverter.toResponse(receivingEntity);
        UserEntity userEntity = userService.getUserBy(receivingEntity.getUserId());
        response.setGoodsIdList(goodsIdList);
        response.setUserName(userEntity.getName());
        return response;

    }

    public ReceivingResponseList showReservationByDate(String date) {

        RequestDateTime dateTime = DateTimeUtils.getStartAndDueDate(date);
        List<ReceivingEntity> receivingEntityList = receivingService.getRequestListByDate(
            dateTime.getStartDateTime(), dateTime.getDueDateTime());

        return getResponseList(receivingEntityList);

    }

    private ReceivingResponseList getResponseList(List<ReceivingEntity> receivingEntityList) {
        ReceivingResponseList responseList = receivingConverter.toResponseList(receivingEntityList);

        receivingEntityList.forEach(receivingEntity -> {

            log.info("receivingList Id : {} ", receivingEntity.getId());

            List<Long> goodsIdList = goodsService.getReceivingGoodsList(receivingEntity.getId())
                .stream().map(goodsEntity -> {
                    return goodsEntity.getId();
                }).toList();

            responseList.getReservationResponseList().forEach(reservationResponse -> {
                reservationResponse.setGoodsIdList(goodsIdList);
                reservationResponse.setUserName(
                    userService.getUserBy(receivingEntity.getUserId()).getName());
            });

        });

        return responseList;
    }

    public ReceivingResponse deliveryStart(Long requestId) {

        ReceivingEntity receivingEntity = receivingService.getRequestBy(requestId);

        if (receivingEntity.getStatus() != ReceivingStatus.CONFIRMATION) {
            throw new ReceivingNotInConfirmationException(
                ReceivingErrorCode.RECEIVING_NOT_IN_CONFIRMATION);
        }

        ReceivingEntity updateEntity = receivingService.startDelivery(receivingEntity);

        ReceivingResponse receivingResponse = setGoodsIdAndUserNameReceivingResponse(updateEntity);

        receivingResponse.getGoodsIdList().forEach(goodsId -> {
            GoodsEntity goodsEntity = goodsService.getGoodsBy(goodsId);
            if (goodsEntity.getStatus() != GoodsStatus.RECEIVING){
                throw new GoodsNotInReceivingException(GoodsErrorCode.GOODS_NOT_IN_RECEIVING);
            }
        });

        return receivingResponse;
    }
}
