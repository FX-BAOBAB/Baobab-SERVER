package delivery.domain.goods.controller.model;

import db.domain.image.enums.ImageKind;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageSet {

    private Long imageId;
    private String caption;
    private ImageKind kind;

}
