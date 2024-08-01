package warehouse.domain.usedgoods.controller.model.request;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchCondition {

    private Long usedGoodsId;

    private String keyword; // 제목으로 찾기

    @Builder.Default
    private int minPrice = 0; // 최소 가격

    private Integer maxPrice; // 최대 가격

    private LocalDateTime startDate; // 시작 날짜

    private LocalDateTime endDate; // 종료 날짜

}
