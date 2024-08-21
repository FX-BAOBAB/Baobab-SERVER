package delivery.domain.image.converter;

import db.domain.image.ImageEntity;
import delivery.domain.goods.controller.model.ImageSet;
import global.annotation.Converter;
import java.util.List;

@Converter
public class ImageConverter {

    public List<ImageSet> toImageSetList(List<ImageEntity> imageEntityList) {
        return imageEntityList.stream().map(imageEntity -> {
            return toImageSet(imageEntity);
        }).toList();
    }

    public ImageSet toImageSet(ImageEntity imageEntity) {
        return ImageSet.builder()
            .imageId(imageEntity.getId())
            .caption(imageEntity.getCaption())
            .kind(imageEntity.getKind())
            .build();
    }


}
