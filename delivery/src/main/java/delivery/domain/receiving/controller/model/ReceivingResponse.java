package delivery.domain.receiving.controller.model;

import db.domain.receiving.enums.ReceivingStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReceivingResponse {

    private Long id;

    private LocalDateTime visitDate;

    private String visitAddress;

    private ReceivingStatus status;

    private LocalDateTime guaranteeAt;

    private String userName;

    private List<Long> goodsIdList;

}
