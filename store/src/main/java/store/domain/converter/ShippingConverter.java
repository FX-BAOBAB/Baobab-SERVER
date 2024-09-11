package store.domain.converter;

import db.domain.receiving.ReceivingEntity;
import db.domain.shipping.ShippingEntity;
import global.annotation.Converter;
import store.domain.controller.model.ReceivingResponse;
import store.domain.controller.model.ShippingResponse;

@Converter
public class ShippingConverter {


    public ShippingResponse toResponse(ShippingEntity shippingEntity) {
        return ShippingResponse.builder()
            .id(shippingEntity.getId())
            .deliveryAddress(shippingEntity.getDeliveryAddress())
            .status(shippingEntity.getStatus())
            .deliveryDate(shippingEntity.getDeliveryDate())
            .userId(shippingEntity.getUserId())
            .deliveryMan(shippingEntity.getDeliveryMan())
            .build();
    }
}
