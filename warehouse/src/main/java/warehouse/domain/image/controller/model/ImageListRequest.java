package warehouse.domain.image.controller.model;

import db.domain.image.enums.ImageKind;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotEmpty
    @Size(min = 1, max = 10)
    private List<MultipartFile> files;

    @NotNull
    private ImageKind kind;

    @NotNull
    @Size(min = 1, max = 10)
    private List<@NotBlank @Size(max = 200) String> captions;

}
