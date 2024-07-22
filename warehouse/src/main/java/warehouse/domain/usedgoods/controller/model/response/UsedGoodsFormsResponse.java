package warehouse.domain.usedgoods.controller.model.response;

import jakarta.persistence.Column;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsedGoodsFormsResponse {

    private String title;

    private int price;

    private String description;

    private LocalDateTime postedAt;

}