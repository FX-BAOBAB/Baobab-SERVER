package warehouse.domain.image.controller.model;

import db.domain.image.enums.ImageKind;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    @NotEmpty
    private MultipartFile file;

    @NotNull
    private ImageKind kind;

    @NotBlank
    private String caption;

}
