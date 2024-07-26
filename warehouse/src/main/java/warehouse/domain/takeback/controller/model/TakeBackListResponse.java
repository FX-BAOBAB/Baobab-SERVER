package warehouse.domain.takeback.controller.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TakeBackListResponse {

    List<TakeBackResponse> takeBackResponseList;

}
