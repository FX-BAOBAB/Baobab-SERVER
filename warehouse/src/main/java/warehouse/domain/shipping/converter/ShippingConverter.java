package warehouse.domain.shipping.converter;

import db.domain.shipping.ShippingEntity;
import db.domain.shipping.enums.ShippingStatus;
import global.annotation.Converter;
import java.util.Arrays;
import java.util.List;
import warehouse.domain.goods.controller.model.GoodsResponse;
import warehouse.domain.shipping.controller.model.request.ShippingRequest;
import warehouse.domain.shipping.controller.model.response.MessageResponse;
import warehouse.domain.shipping.controller.model.response.ShippingListResponse;
import warehouse.domain.shipping.controller.model.response.ShippingDetailResponse;
import warehouse.domain.shipping.controller.model.response.ShippingStatusResponse;

@Converter
public class ShippingConverter {

    public ShippingEntity toEntity(ShippingRequest request, Long userId) {
        return ShippingEntity.builder()
            .userId(userId)
            .deliveryDate(request.getDeliveryDate())
            .deliveryAddress(request.getDeliveryAddress())
            .build();
    }

    public MessageResponse toMessageResponse(String message) {
        return MessageResponse.builder()
            .message(message)
            .build();
    }

    public ShippingListResponse toResponse(List<ShippingDetailResponse> shippingResponses) {
        return ShippingListResponse.builder()
            .shippingList(shippingResponses)
            .build();
    }

    public ShippingDetailResponse toResponse(ShippingEntity entity, List<GoodsResponse> goodsResponses) {
        return ShippingDetailResponse.builder()
            .shippingId(entity.getId())
            .deliveryDate(entity.getDeliveryDate())
            .deliveryAddress(entity.getDeliveryAddress())
            .status(entity.getStatus())
            .deliveryMan(entity.getDeliveryMan())
            .goods(goodsResponses)
            .build();
    }

    public ShippingStatusResponse toCurrentStatusResponse(ShippingEntity shippingEntity) {
        return ShippingStatusResponse.builder()
            .shippingId(shippingEntity.getId())
            .total(Arrays.stream(ShippingStatus.values()).count())
            .status(shippingEntity.getStatus())
            .description(shippingEntity.getStatus().getDescription())
            .current(shippingEntity.getStatus().getCurrent())
            .build();
    }

}
