package store.domain.management.converter;

import db.domain.shipping.ShippingEntity;
import global.annotation.Converter;
import store.domain.management.controller.model.ShippingResponse;

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
