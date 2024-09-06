package fault.domain.fault.controller.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FaultRequest {

    @NotNull
    private Long receivingId;

    @NotBlank
    @Size(max = 200)
    private String description;

}
