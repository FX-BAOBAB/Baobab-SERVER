package warehouse.domain.goods.controller.model;

import db.domain.goods.enums.GoodsCategory;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import warehouse.domain.image.controller.model.ImageResponse;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoodsResponse {

    private Long id;

    private String name;

    private GoodsCategory category;

    private int quantity;

    private List<ImageResponse> basicImages;

    private List<ImageResponse> faultImages;

}
