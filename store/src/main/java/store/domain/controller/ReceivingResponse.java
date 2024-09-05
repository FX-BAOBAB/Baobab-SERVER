package store.domain.controller;

import db.domain.receiving.enums.ReceivingStatus;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReceivingResponse {

    private Long id;
    private String visitAddress;
    private LocalDateTime visitDate;
    private LocalDateTime guaranteeAt;
    private ReceivingStatus status;
    private List<Long> goodsIdList;

}
