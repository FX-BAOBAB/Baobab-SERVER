package warehouse.domain.takeback.controller.model;

import db.domain.takeback.enums.TakeBackStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TakeBackResponse {

    private TakeBackStatus status;
    private LocalDateTime takeBackRequestAt;

}
