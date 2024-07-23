package warehouse.domain.shipping.controller.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShippingRequest {

    private LocalDateTime deliveryDate;

    @NotBlank
    @Size(max = 200)
    private String deliveryAddress;

    @NotNull
    @Size(min = 1)
    private List<Long> goodsId;

}
