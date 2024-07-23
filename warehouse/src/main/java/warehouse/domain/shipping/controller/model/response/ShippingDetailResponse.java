package warehouse.domain.shipping.controller.model.response;

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

    private ShippingResponse shipping;
    private List<GoodsResponse> goods;

}
