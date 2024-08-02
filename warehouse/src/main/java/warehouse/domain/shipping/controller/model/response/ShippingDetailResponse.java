package warehouse.domain.shipping.controller.model.response;

import db.domain.shipping.enums.ShippingStatus;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import warehouse.domain.goods.controller.model.GoodsResponse;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShippingDetailResponse {

    private Long shippingId;

    private LocalDateTime deliveryDate;

    private String deliveryAddress;

    private ShippingStatus status;

    private Long deliveryMan;

    private List<GoodsResponse> goods;
}
