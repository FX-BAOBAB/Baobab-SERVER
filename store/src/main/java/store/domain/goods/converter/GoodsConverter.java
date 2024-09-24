package store.domain.goods.converter;

import db.domain.goods.GoodsEntity;
import global.annotation.Converter;
import store.domain.goods.controller.model.GoodsResponse;

@Converter
public class GoodsConverter {

    public GoodsResponse toResponse(GoodsEntity goodsEntity) {
        return GoodsResponse.builder()
            .id(goodsEntity.getId())
            .name(goodsEntity.getName())
            .modelName(goodsEntity.getModelName())
            .category(goodsEntity.getCategory())
            .quantity(goodsEntity.getQuantity())
            .abandonmentAt(goodsEntity.getAbandonmentAt())
            .status(goodsEntity.getStatus())
            .build();
    }



}
