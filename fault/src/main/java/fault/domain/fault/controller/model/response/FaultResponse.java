package fault.domain.fault.controller.model.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FaultResponse {

    private Long faultId;
    private String description;
    private Boolean approval;
    private LocalDateTime registeredAt;
    private Long receivingId;

}
