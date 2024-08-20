package delivery.domain.receiving.business;

import db.domain.goods.GoodsEntity;
import db.domain.receiving.ReceivingEntity;
import db.domain.users.UserEntity;
import delivery.domain.goods.converter.GoodsConverter;
import delivery.domain.goods.service.GoodsService;
import delivery.domain.receiving.controller.model.ReceivingResponse;
import delivery.domain.receiving.controller.model.ReceivingResponseList;
import delivery.domain.receiving.converter.ReceivingConverter;
import delivery.domain.receiving.service.ReceivingService;
import delivery.domain.users.converter.UserConverter;
import delivery.domain.users.service.UserService;
import global.annotation.Business;
import java.util.ArrayList;
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

}
