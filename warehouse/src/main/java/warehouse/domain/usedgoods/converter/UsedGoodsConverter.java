package warehouse.domain.usedgoods.converter;

import db.domain.usedgoods.UsedGoodsEntity;
import global.annotation.Converter;
import warehouse.domain.goods.controller.model.GoodsResponse;
import warehouse.domain.usedgoods.controller.model.request.RegisterUsedGoods;
import warehouse.domain.usedgoods.controller.model.response.MessageResponse;
import warehouse.domain.usedgoods.controller.model.response.UsedGoodsDetailResponse;

@Converter
public class UsedGoodsConverter {

    public UsedGoodsEntity toEntity(RegisterUsedGoods request, Long userId) {
        return UsedGoodsEntity.builder()
            .title(request.getTitle())
            .price(request.getPrice())
            .description(request.getDescription())
            .goodsId(request.getGoodsId())
            .userId(userId)
            .build();
    }

    public UsedGoodsDetailResponse toResponse(UsedGoodsEntity usedGoodsEntity, GoodsResponse goodsResponse) {
        return UsedGoodsDetailResponse.builder()
            .title(usedGoodsEntity.getTitle())
            .price(usedGoodsEntity.getPrice())
            .description(usedGoodsEntity.getDescription())
            .postedAt(usedGoodsEntity.getPostedAt())
            .goods(goodsResponse)
            .build();
    }

    public MessageResponse toMessageResponse(String message) {
        return MessageResponse.builder()
            .message(message)
            .build();
    }
}
