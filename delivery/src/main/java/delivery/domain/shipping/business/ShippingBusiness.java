package delivery.domain.shipping.business;

import db.domain.receiving.ReceivingEntity;
import db.domain.shipping.ShippingEntity;
import db.domain.users.UserEntity;
import delivery.domain.goods.converter.GoodsConverter;
import delivery.domain.goods.service.GoodsService;
import delivery.domain.receiving.controller.model.ReceivingResponseList;
import delivery.domain.shipping.controller.model.ShippingResponse;
import delivery.domain.shipping.controller.model.ShippingResponseList;
import delivery.domain.shipping.converter.ShippingConverter;
import delivery.domain.shipping.service.ShippingService;
import delivery.domain.users.converter.UserConverter;
import delivery.domain.users.service.UserService;
import global.annotation.Business;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Business
@RequiredArgsConstructor
public class ShippingBusiness {

    private final ShippingService shippingService;
    private final ShippingConverter shippingConverter;
    private final UserService userService;
    private final UserConverter userConverter;
    private final GoodsService goodsService;
    private final GoodsConverter goodsConverter;

    public ShippingResponseList getReservationList() {

        List<ShippingEntity> shippingEntityList = shippingService.getRequestList();

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
                reservationResponse.setUserName(userService.getUserBy(shippingEntity.getUserId()).getName());
            });

        });

        return responseList;

    }

    public ShippingResponse getReservation(Long requestId) {
        ShippingEntity shippingEntity = shippingService.getRequest(requestId);

        List<Long> goodsIdList = goodsService.getShippingGoodsList(shippingEntity.getId()).stream().map(
            goodsEntity -> {
                return goodsEntity.getId();
            }
        ).toList();

        ShippingResponse response = shippingConverter.toResponse(shippingEntity);
        UserEntity userEntity = userService.getUserBy(shippingEntity.getUserId());

        response.setUserName(userEntity.getName());
        response.setGoodsIdList(goodsIdList);

        return response;
    }
}
