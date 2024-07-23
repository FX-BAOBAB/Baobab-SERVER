package warehouse.domain.shipping.converter;

import db.domain.shipping.ShippingEntity;
import global.annotation.Converter;
import java.util.List;
import java.util.stream.Collectors;
import warehouse.domain.goods.controller.model.GoodsResponse;
import warehouse.domain.shipping.controller.model.request.ShippingRequest;
import warehouse.domain.shipping.controller.model.response.MessageResponse;
import warehouse.domain.shipping.controller.model.response.ShippingDetailResponse;
import warehouse.domain.shipping.controller.model.response.ShippingListResponse;
import warehouse.domain.shipping.controller.model.response.ShippingResponse;

@Converter
public class ShippingConverter {

    public ShippingEntity toEntity(ShippingRequest request) {
        return ShippingEntity.builder()
            .deliveryDate(request.getDeliveryDate())
            .deliveryAddress(request.getDeliveryAddress())
            .build();
    }

    public MessageResponse toMessageResponse(String message) {
        return MessageResponse.builder()
            .message(message)
            .build();
    }

    public ShippingListResponse toResponseList(List<ShippingEntity> entityList) {

        List<ShippingResponse> shippingResponses = entityList.stream()
            .map(entity -> this.toResponse(entity)).collect(Collectors.toList());

        return ShippingListResponse.builder()
            .shipping(shippingResponses)
            .build();
    }

    public ShippingResponse toResponse(ShippingEntity entity) {
        return ShippingResponse.builder()
            .shippingId(entity.getId())
            .deliveryDate(entity.getDeliveryDate())
            .deliveryAddress(entity.getDeliveryAddress())
            .status(entity.getStatus())
            .deliveryMan(entity.getDeliveryMan())
            .build();
    }
}
