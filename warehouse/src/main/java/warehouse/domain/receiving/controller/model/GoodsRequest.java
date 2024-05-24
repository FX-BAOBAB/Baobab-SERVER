package warehouse.domain.receiving.controller.model;

import db.domain.goods.enums.GoodsCategory;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoodsRequest {

    // TODO List Valid 추가 필요

    private String name;
    private String modelName;
    private GoodsCategory category;
    private int quantity;
    private List<MultipartFile> basicImage;
    private List<MultipartFile> faultImage;

}
