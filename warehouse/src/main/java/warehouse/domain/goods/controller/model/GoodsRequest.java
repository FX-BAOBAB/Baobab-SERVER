package warehouse.domain.goods.controller.model;

import db.domain.goods.enums.GoodsCategory;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoodsRequest {

    @NotBlank
    @Size(min = 1, max = 20) //이름 길이
    private String name;

    @NotBlank
    @Size(min = 1, max = 20) //이름 길이
    private String modelName;

    @NotNull
    private GoodsCategory category;

//    @Positive
    @Min(value = 1) @Max(value = 2)
    private int quantity;

    @NotNull
    @Size(min = 1, max = 2)
    private List<Long> imageIdList;

}
