package fault.domain.fault.controller.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddFaultRequest {

    @NotNull
    private Long imageId;

    @NotNull
    private Long goodsId;

}
