package warehouse.domain.takeback.controller.model;

import db.domain.takeback.enums.TakeBackStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TakeBackStatusResponse {

    private Long takeBackId;
    private long total;
    private TakeBackStatus status;
    private int current;
    private String description;

}
