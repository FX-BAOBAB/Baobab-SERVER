package warehouse.domain.takeback.controller.model;

import db.domain.takeback.enums.TakeBackStatus;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import warehouse.domain.goods.controller.model.GoodsResponse;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TakeBackResponse {

    private Long id;
    private TakeBackStatus status;
    private LocalDateTime takeBackRequestAt;
    private List<GoodsResponse> goods;
    private Long userId;

}
