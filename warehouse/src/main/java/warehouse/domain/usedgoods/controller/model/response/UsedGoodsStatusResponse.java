package warehouse.domain.usedgoods.controller.model.response;

import db.domain.usedgoods.enums.UsedGoodsStatus;
import db.domain.usedgoodsorder.enums.UsedGoodsOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsedGoodsStatusResponse {

    UsedGoodsStatus usedGoodsStatus;

}
