package warehouse.domain.usedgoods.controller.model.response;

import db.domain.usedgoods.enums.UsedGoodsStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import warehouse.domain.goods.controller.model.GoodsResponse;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsedGoodsSearchResponse {

    Long usedGoodsId;

    private String title;

    private int price;

    private LocalDateTime postedAt;

    private UsedGoodsStatus status;

    private GoodsResponse goods;

}
