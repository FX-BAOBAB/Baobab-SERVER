package store.domain.image.domain.image.controller.model;

import db.domain.image.enums.ImageKind;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageResponse {

    private Long id;

    private String serverName;

    private String originalName;

    private String imageUrl;

    private String caption;

    private ImageKind kind;

    private Long goodsId;

}
