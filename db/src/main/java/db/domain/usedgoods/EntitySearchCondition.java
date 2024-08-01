package db.domain.usedgoods;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntitySearchCondition {

    private Long usedGoodsId;
    private String keyword;
    private int minPrice;
    private Integer maxPrice;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Pageable page;
    private Long userId;

}
