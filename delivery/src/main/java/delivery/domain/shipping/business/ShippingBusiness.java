package delivery.domain.shipping.business;

import db.domain.goods.GoodsEntity;
import db.domain.goods.enums.GoodsStatus;
import db.domain.shipping.ShippingEntity;
import db.domain.shipping.enums.ShippingStatus;
import db.domain.users.UserEntity;
import delivery.common.error.GoodsErrorCode;
import delivery.common.error.ShippingErrorCode;
import delivery.common.exception.goods.GoodsNotInShippingIngException;
import delivery.common.exception.shipping.ShippingNotInRegisteredException;
import delivery.common.utils.datetime.DateTimeUtils;
import delivery.common.utils.datetime.DateTimeUtils.RequestDateTime;
import delivery.domain.goods.converter.GoodsConverter;
import delivery.domain.goods.service.GoodsService;
import delivery.domain.shipping.controller.model.ShippingResponse;
import delivery.domain.shipping.controller.model.ShippingResponseList;
import delivery.domain.shipping.converter.ShippingConverter;
import delivery.domain.shipping.service.ShippingService;
import delivery.domain.users.converter.UserConverter;
import delivery.domain.users.security.jwt.service.UsersService;
import global.annotation.Business;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;

@Slf4j
@Business
@RequiredArgsConstructor
public class ShippingBusiness {

    private final ShippingService shippingService;
    private final ShippingConverter shippingConverter;
    private final UsersService userService;
    private final GoodsService goodsService;

    public ShippingResponseList getReservationList() {

        List<ShippingEntity> shippingEntityList = shippingService.getRequestList();

        return getResponseList(shippingEntityList);
    }

    public ShippingResponse getReservation(Long requestId) {
        ShippingEntity shippingEntity = shippingService.getRequest(requestId);

        ShippingResponse response = getShippingResponse(shippingEntity);

        return response;
    }

    public ShippingResponse shippingReservation(Long requestId, User user) {

        Long deliveryManId = userService.getUserWithThrow(user.getUsername()).getId();

        ShippingEntity shippingEntity = shippingService.reservationConfirmed(requestId,deliveryManId);

        ShippingResponse response = getShippingResponse(shippingEntity);

        return response;
    }

    private ShippingResponse getShippingResponse(ShippingEntity shippingEntity) {
        ShippingResponse response = shippingConverter.toResponse(shippingEntity);

        List<Long> goodsIdList = goodsService.getShippingGoodsList(shippingEntity.getId()).stream().map(
            goodsEntity -> {
                return goodsEntity.getId();
            }
        ).toList();

        UserEntity userEntity = userService.getUserWithThrow(shippingEntity.getUserId());

        response.setUserName(userEntity.getName());
        response.setGoodsIdList(goodsIdList);

        Long deliveryManId = shippingEntity.getDeliveryMan();
        if (deliveryManId != null) {
            UserEntity deliveryMan = userService.getUserWithThrow(deliveryManId);
            response.setDeliveryMan(deliveryMan.getName());
        }

        return response;
    }

    public ShippingResponseList showReservationByDate(String date, User user) {

        Long deliveryManId = userService.getUserWithThrow(user.getUsername()).getId();

        RequestDateTime dateTime = DateTimeUtils.getStartAndDueDate(date);

        List<ShippingEntity> shippingEntityList = shippingService.getRequestListByDate(dateTime.getStartDateTime(),dateTime.getDueDateTime(),deliveryManId);

        return getResponseList(shippingEntityList);
    }

    private ShippingResponseList getResponseList(List<ShippingEntity> shippingEntityList) {
        ShippingResponseList responseList = shippingConverter.toResponseList(shippingEntityList);

        shippingEntityList.forEach(shippingEntity -> {

            log.info("receivingList Id : {} " , shippingEntity.getId());

            List<Long> goodsIdList = goodsService.getShippingGoodsList(shippingEntity.getId()).stream().map(
                goodsEntity -> {
                    return goodsEntity.getId();
                }
            ).toList();

            responseList.getReservationResponseList().forEach(reservationResponse -> {
                reservationResponse.setGoodsIdList(goodsIdList);
                reservationResponse.setUserName(userService.getUserWithThrow(shippingEntity.getUserId()).getName());

                Long deliveryManId = shippingEntity.getDeliveryMan();

                if (deliveryManId != null) {
                    UserEntity deliveryMan = userService.getUserWithThrow(deliveryManId);
                    reservationResponse.setDeliveryMan(deliveryMan.getName());
                }

            });

        });

        return responseList;
    }

    public ShippingResponse deliveryStart(Long requestId) {

        ShippingEntity shippingEntity = shippingService.getRequest(requestId);

        if (shippingEntity.getStatus() != ShippingStatus.REGISTERED) {
            throw new ShippingNotInRegisteredException(
                ShippingErrorCode.SHIPPING_NOT_IN_REGISTERED);
        }

        ShippingEntity updateEntity = shippingService.startDelivery(shippingEntity);

        ShippingResponse shippingResponse = getShippingResponse(updateEntity);

        shippingResponse.getGoodsIdList().forEach(goodsId -> {
            GoodsEntity goodsEntity = goodsService.getGoodsBy(goodsId);
            if (goodsEntity.getStatus() != GoodsStatus.SHIPPING_ING){
                throw new GoodsNotInShippingIngException(GoodsErrorCode.GOODS_NOT_IN_SHIPPING_ING);
            }
        });

        return shippingResponse;
    }

    public ShippingResponse deliveryComplete(Long requestId) {

        ShippingEntity shippingEntity = shippingService.getRequest(requestId);

        if (shippingEntity.getStatus() != ShippingStatus.DELIVERY) {
            throw new ShippingNotInRegisteredException(
                ShippingErrorCode.SHIPPING_NOT_IN_DELIVERY);
        }

        ShippingEntity updateEntity = shippingService.deliveryComplete(shippingEntity);

        ShippingResponse shippingResponse = getShippingResponse(updateEntity);

        shippingResponse.getGoodsIdList().forEach(goodsId -> {
            GoodsEntity goodsEntity = goodsService.getGoodsBy(goodsId);
            goodsService.changeShippingComplete(goodsEntity);
        });

        return shippingResponse;

    }
}
