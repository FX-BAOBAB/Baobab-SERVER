package warehouse.domain.usedgoods.controller.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsedGoodsPostRequest {

    @NotBlank @Size(min=2)
    private String title;

    @PositiveOrZero
    private int price;

    @Size(min= 2, max = 500)
    private String description;

    @NotNull
    private Long goodsId;

}
