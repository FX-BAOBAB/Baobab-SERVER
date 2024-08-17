package warehouse.domain.usedgoods.controller.model.response;

import db.domain.usedgoodsorder.enums.UsedGoodsOrderStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsedGoodsOrderResponse {

    private Long usedGoodsOrderId;
    private Long userId; // 구매자 ID
    private UsedGoodsOrderStatus status;
    private LocalDateTime createdAt;
    private Long usedGoodsId;

}
