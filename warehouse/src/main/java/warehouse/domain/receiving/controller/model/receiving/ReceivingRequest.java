package warehouse.domain.receiving.controller.model.receiving;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import warehouse.domain.goods.controller.model.GoodsRequest;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceivingRequest {

    @NotNull
    private LocalDateTime visitDate;

    @NotBlank
    @Size(min = 1, max = 200)
    private String visitAddress;

    @NotEmpty
    @Valid
    private List<GoodsRequest> goodsRequests;

    @NotNull
    private LocalDateTime guaranteeAt;

}
