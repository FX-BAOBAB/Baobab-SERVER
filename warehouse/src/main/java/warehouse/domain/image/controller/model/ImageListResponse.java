package warehouse.domain.image.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageListResponse {

    private ImageList basicImageListResponse;

    private ImageList faultImageListResponse;

}
