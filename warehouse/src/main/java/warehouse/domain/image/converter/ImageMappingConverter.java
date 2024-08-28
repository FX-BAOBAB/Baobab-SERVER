package warehouse.domain.image.converter;

import db.domain.imagemapping.ImageMappingEntity;
import global.annotation.Converter;
import warehouse.domain.image.controller.model.ImageRequest;

@Converter
public class ImageMappingConverter {

    public ImageMappingEntity toEntity(ImageRequest request) {
        return ImageMappingEntity.builder()
            .kind(request.getKind())
            .build();
    }

}
