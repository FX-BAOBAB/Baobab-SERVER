package warehouse.domain.receiving.controller.model.receiving;

import db.domain.receiving.enums.ReceivingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReceivingStatusResponse {

    private Long receivingId;
    private long total;
    private ReceivingStatus status;
    private int current;
    private String description;

}
