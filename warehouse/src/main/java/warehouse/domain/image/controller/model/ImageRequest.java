package warehouse.domain.image.controller.model;

import db.domain.image.enums.ImageKind;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageRequest {

    private MultipartFile file;

    private Long goodsId;

    private ImageKind kind;

    private String caption;

}
