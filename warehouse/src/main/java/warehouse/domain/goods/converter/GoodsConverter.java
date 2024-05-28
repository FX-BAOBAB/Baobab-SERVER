package warehouse.domain.goods.converter;

import db.domain.goods.GoodsEntity;
import global.annotation.Converter;
import lombok.RequiredArgsConstructor;
import warehouse.domain.goods.controller.model.GoodsRequest;
import warehouse.domain.goods.controller.model.GoodsResponse;
import warehouse.domain.image.controller.model.ImageListResponse;

@Converter
@RequiredArgsConstructor
public class GoodsConverter {

    public GoodsEntity toEntity(GoodsRequest request) {
        return GoodsEntity.builder()
            .name(request.getName())
            .modelName(request.getModelName())
            .category(request.getCategory())
            .quantity(request.getQuantity())
            .build();
    }

    public GoodsResponse toResponse(GoodsEntity goodsEntity, ImageListResponse imageListResponse) {
        return GoodsResponse.builder()
            .id(goodsEntity.getId())
            .name(goodsEntity.getName())
            .basicImages(imageListResponse.getBasicImageListResponse())
            .faultImages(imageListResponse.getFaultImageListResponse())
            .category(goodsEntity.getCategory())
            .quantity(goodsEntity.getQuantity())
            .build();
    }

}
