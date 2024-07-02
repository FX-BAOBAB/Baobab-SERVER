package warehouse.domain.receiving.controller.model.receiving;

import db.domain.receiving.enums.ReceivingStatus;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import warehouse.domain.goods.controller.model.GoodsResponse;
import warehouse.domain.takeback.controller.model.TakeBackResponse;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReceivingResponse {

    private Long id;

    private String visitAddress;

    private LocalDateTime visitDate;

    private LocalDateTime guaranteeAt;

    private ReceivingStatus receivingStatus;

    private List<GoodsResponse> goods;

    private TakeBackResponse takeBackResponse;

}
