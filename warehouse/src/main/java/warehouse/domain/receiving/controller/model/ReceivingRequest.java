package warehouse.domain.receiving.controller.model;


import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import warehouse.domain.goods.controller.model.GoodsRequest;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceivingRequest {

    // TODO List Valid 추가 필요

    private List<GoodsRequest> goodsRequest;

    private LocalDateTime guaranteeAt;

}
