package warehouse.domain.usedgoods.controller.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime postedAt;

    private UsedGoodsStatus status;

    private GoodsResponse goods;

}
