package store.domain.store.controller.model;

import db.domain.goods.enums.GoodsCategory;
import db.domain.goods.enums.GoodsStatus;
import db.domain.store.enums.StoreLocation;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import store.domain.goods.controller.model.ImageUrlSet;

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
