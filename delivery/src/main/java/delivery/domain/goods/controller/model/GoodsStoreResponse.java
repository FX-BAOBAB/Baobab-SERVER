package delivery.domain.goods.controller.model;

import db.domain.goods.enums.GoodsCategory;
import db.domain.goods.enums.GoodsStatus;
import db.domain.store.enums.StoreLocation;
import delivery.domain.image.controller.model.ImageUrlSet;
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
public class GoodsStoreResponse {

    private Long id;

    private String name;

    private String modelName;

    private GoodsCategory category;

    private int quantity;

    private LocalDateTime abandonmentAt;

    private GoodsStatus status;

    private List<ImageUrlSet> basicImageUrlSet;

    private List<ImageUrlSet> faultImageUrlSet;

    private StoreLocation storeLocation;

    private LocalDateTime storedAt;

}
