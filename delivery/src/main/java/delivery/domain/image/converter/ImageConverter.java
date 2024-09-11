package delivery.domain.image.converter;

import db.domain.image.ImageEntity;
import db.domain.imagemapping.ImageMappingEntity;
import delivery.domain.goods.controller.model.ImageSet;
import delivery.domain.image.service.ImageMappingService;
import global.annotation.Converter;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Converter
@RequiredArgsConstructor
public class ImageConverter {

    private final ImageMappingService imageMappingService;

    public List<ImageSet> toImageSetList(List<ImageEntity> imageEntityList) {
        return imageEntityList.stream().map(imageEntity -> {
            return toImageSet(imageEntity);
        }).toList();
    }

    public ImageSet toImageSet(ImageEntity imageEntity) {
        ImageMappingEntity imageMappingEntity = imageMappingService.getImageMappingBy(
            imageEntity.getImageMappingId());
        return ImageSet.builder()
            .imageId(imageEntity.getId())
            .caption(imageEntity.getCaption())
            .kind(imageMappingEntity.getKind())
            .build();
    }


}
