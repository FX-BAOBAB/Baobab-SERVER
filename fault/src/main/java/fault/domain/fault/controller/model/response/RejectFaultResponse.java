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
public class RejectFaultResponse {

    private Long faultId;
    private Long receivingId;
    private String description;
    private LocalDateTime guaranteeAt;

}
