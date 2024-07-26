package warehouse.domain.receiving.controller.model.receiving;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceivingListResponse {

    List<ReceivingResponse> receivingResponseList;

}
