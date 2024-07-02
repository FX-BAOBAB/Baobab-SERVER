package warehouse.domain.image.controller.model;

import db.domain.image.enums.ImageKind;
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
public class ImageListRequest {

    // TODO Valid 필요

    private List<MultipartFile> files;

    private ImageKind kind;

    private List<String> captions;

}
