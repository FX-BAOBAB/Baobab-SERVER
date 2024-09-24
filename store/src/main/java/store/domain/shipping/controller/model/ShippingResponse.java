package store.domain.shipping.controller.model;

import db.domain.shipping.enums.ShippingStatus;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import store.domain.goods.controller.model.GoodsResponse;

@Data
@Builder
public class ShippingResponse {

    private Long id;

    private LocalDateTime deliveryDate;

    private String deliveryAddress;

    private ShippingStatus status;

    private Long userId;

    private Long deliveryMan;

    private List<GoodsResponse> goodsList;

}
