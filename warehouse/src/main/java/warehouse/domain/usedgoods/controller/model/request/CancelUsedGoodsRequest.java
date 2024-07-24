package warehouse.domain.usedgoods.controller.model.request;

import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CancelUsedGoodsRequest {

    @Size(min = 1)
    List<Long> usedGoodsIdList;

}
