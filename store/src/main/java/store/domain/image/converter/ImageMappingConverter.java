package store.domain.image.converter;

import db.domain.image.enums.ImageKind;
import db.domain.imagemapping.ImageMappingEntity;
import global.annotation.Converter;
import store.domain.image.controller.model.ImageRequest;

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
