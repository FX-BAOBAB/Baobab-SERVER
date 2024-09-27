package delivery.domain.fault.controller.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddFaultRequest {

    @NotNull
    private List<MultipartFile> files;

    @NotBlank
    @Size(max = 200)
    private List<String> caption;

    @NotNull
    private Long goodsId;

}
