package warehouse.domain.receiving.controller.model;


import db.domain.receiving.enums.ReceivingStatus;
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
    private LocalDateTime visitDate;

    private String visitAddress;

    private ReceivingStatus receivingStatus;

    private List<GoodsRequest> goodsRequests;

    private LocalDateTime guaranteeAt;

}