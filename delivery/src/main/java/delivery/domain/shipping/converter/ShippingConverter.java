package delivery.domain.shipping.converter;

import db.domain.shipping.ShippingEntity;
import delivery.domain.shipping.controller.model.ShippingResponse;
import delivery.domain.shipping.controller.model.ShippingResponseList;
import global.annotation.Converter;
import java.util.List;

@Converter
public class ShippingConverter {

    public ShippingResponseList toResponseList(List<ShippingEntity> shippingEntityList){
        List<ShippingResponse> responseList = shippingEntityList.stream().map(shippingEntity -> {
            return toResponse(shippingEntity);
        }).toList();

        return ShippingResponseList.builder()
            .reservationResponseList(responseList)
            .build();
    }

    public ShippingResponse toResponse(ShippingEntity shippingEntity) {
        return ShippingResponse.builder()
            .id(shippingEntity.getId())
            .status(shippingEntity.getStatus())
            .deliveryDate(shippingEntity.getDeliveryDate())
            .deliveryAddress(shippingEntity.getDeliveryAddress())
            .deliveryMan(shippingEntity.getDeliveryMan())
            .build();
    }
}
