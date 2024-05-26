package warehouse.domain.image.controller.model;

import db.domain.image.enums.ImageKind;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    private String caption;

    private ImageKind kind;

    private Long goodsId;

    private String extension;

}
