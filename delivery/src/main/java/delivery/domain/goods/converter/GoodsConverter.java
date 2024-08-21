package delivery.domain.goods.converter;

import db.domain.goods.GoodsEntity;
import delivery.domain.goods.controller.model.GoodsResponse;
import delivery.domain.goods.controller.model.GoodsResponses;
import global.annotation.Converter;
import java.util.List;

@Converter
public class GoodsConverter {

    public GoodsResponses toResponseList(List<GoodsEntity> goodsEntityList) {
        List<GoodsResponse> goodsResponses = goodsEntityList.stream().map(goodsEntity -> {
            return toResponse(goodsEntity);
        }).toList();
        return GoodsResponses.builder()
            .goodsResponseList(goodsResponses)
            .build();
    }

    public GoodsResponse toResponse(GoodsEntity goodsEntity) {
        return GoodsResponse.builder()
            .id(goodsEntity.getId())
            .name(goodsEntity.getName())
            .modelName(goodsEntity.getModelName())
            .status(goodsEntity.getStatus())
            .quantity(goodsEntity.getQuantity())
            .category(goodsEntity.getCategory())
            .abandonmentAt(goodsEntity.getAbandonmentAt())
            .build();
    }

}
