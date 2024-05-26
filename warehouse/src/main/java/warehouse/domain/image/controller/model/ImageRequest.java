package warehouse.domain.image.controller.model;

import db.domain.image.enums.ImageKind;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageRequest {

    private MultipartFile file;

    private ImageKind kind;

    private String caption;

}
