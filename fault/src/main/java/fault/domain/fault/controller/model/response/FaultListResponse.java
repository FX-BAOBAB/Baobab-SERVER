package fault.domain.fault.controller.model.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FaultListResponse {

    private Long goodsId;

    private List<FaultImageResponse> faultList;

}
