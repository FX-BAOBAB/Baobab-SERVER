package warehouse.domain.usedgoods.controller.model.response;

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
public class UsedGoodsDetailResponse {

    private String title;

    private int price;

    private String description;

    private LocalDateTime postedAt;

    private GoodsResponse goods;

}