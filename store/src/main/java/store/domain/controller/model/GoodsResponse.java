package store.domain.controller.model;

import db.domain.goods.enums.GoodsCategory;
import db.domain.goods.enums.GoodsStatus;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsResponse {

    private Long id;

    private String name;

    private String modelName;

    private GoodsCategory category;

    private int quantity;

    private LocalDateTime abandonmentAt;

    private GoodsStatus status;

    private List<String> basicImageUrlList;

    private List<String> faultImageUrlList;

}
