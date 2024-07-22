package warehouse.domain.usedgoods.controller.model.response;

import db.domain.goods.enums.GoodsStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoodsStatusChangeResponse {

    private Long goodsId;
    private GoodsStatus goodsStatus;

}
