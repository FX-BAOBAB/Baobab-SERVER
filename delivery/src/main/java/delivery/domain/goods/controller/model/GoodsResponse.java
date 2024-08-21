package delivery.domain.goods.controller.model;

import db.domain.goods.enums.GoodsCategory;
import db.domain.goods.enums.GoodsStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    private List<ImageSet> images;

}
