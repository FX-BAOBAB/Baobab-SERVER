package warehouse.domain.image.controller.model;

import db.domain.image.enums.ImageKind;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageListRequest {

    private List<MultipartFile> files;

    private Long goodsId;

    private ImageKind kind;

    private List<String> captions;

}
