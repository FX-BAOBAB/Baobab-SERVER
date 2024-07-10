package warehouse.domain.image.controller.model;

import db.domain.image.enums.ImageKind;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotNull
    private MultipartFile file;

    @NotNull
    private ImageKind kind;

    @NotBlank @Size(max = 200)
    private String caption;

}
