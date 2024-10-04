package delivery.domain.image.converter;

import db.domain.image.enums.ImageKind;
import db.domain.imagemapping.ImageMappingEntity;
import delivery.domain.image.controller.model.ImageRequest;
import global.annotation.Converter;

@Converter
public class ImageMappingConverter {

    public ImageMappingEntity toEntity(ImageRequest request) {
        return ImageMappingEntity.builder()
            .kind(request.getKind())
            .build();
    }

    // TODO 임시 kind, userId UPDATE 필요
    public ImageMappingEntity toEntity(ImageRequest request , Long goodsId) {
        return ImageMappingEntity.builder()
            .goodsId(goodsId)
            .kind(ImageKind.FAULT)
            .userId(request.getUserId())
            .build();
    }

}
