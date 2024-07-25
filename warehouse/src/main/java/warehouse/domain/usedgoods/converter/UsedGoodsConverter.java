package warehouse.domain.usedgoods.converter;

import db.domain.goods.GoodsEntity;
import db.domain.usedgoods.UsedGoodsEntity;
import global.annotation.Converter;
import java.util.List;
import java.util.stream.Collectors;
import warehouse.domain.goods.controller.model.GoodsResponse;
import warehouse.domain.usedgoods.controller.model.request.UsedGoodsPostRequest;
import warehouse.domain.usedgoods.controller.model.response.TransformUsedGoodsResponse;
import warehouse.domain.usedgoods.controller.model.response.UsedGoodsPostResponse;

@Converter
public class UsedGoodsConverter {

    public UsedGoodsEntity toEntity(UsedGoodsPostRequest request, Long userId) {
        return UsedGoodsEntity.builder()
            .userId(userId)
            .title(request.getTitle())
            .price(request.getPrice())
            .description(request.getDescription())
            .build();
    }


    public TransformUsedGoodsResponse toResponse(GoodsEntity goodsEntity) {
        return TransformUsedGoodsResponse.builder()
            .goodsId(goodsEntity.getId())
            .goodsStatus(goodsEntity.getStatus())
            .build();
    }

    public List<TransformUsedGoodsResponse> toResponse(List<GoodsEntity> goodsEntityList) {
            return goodsEntityList.stream().map((goodsEntity) -> toResponse(goodsEntity))
                .collect(Collectors.toList());
    }

    public UsedGoodsPostResponse toResponse(UsedGoodsEntity usedGoodsEntity, GoodsResponse goodsResponse) {
        return UsedGoodsPostResponse.builder()
            .title(usedGoodsEntity.getTitle())
            .price(usedGoodsEntity.getPrice())
            .description(usedGoodsEntity.getDescription())
            .postedAt(usedGoodsEntity.getPostedAt())
            .goods(goodsResponse)
            .build();
    }
}
