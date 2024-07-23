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
}
