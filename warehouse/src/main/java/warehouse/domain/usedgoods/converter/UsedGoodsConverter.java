package warehouse.domain.usedgoods.converter;

import db.domain.goods.GoodsEntity;
import db.domain.usedgoods.UsedGoodsEntity;
import global.annotation.Converter;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.userdetails.User;
import warehouse.domain.usedgoods.controller.model.request.UsedGoodsFormsRequest;
import warehouse.domain.usedgoods.controller.model.response.GoodsStatusChangeResponse;
import warehouse.domain.usedgoods.controller.model.response.UsedGoodsFormsResponse;

@Converter
public class UsedGoodsConverter {

    public UsedGoodsEntity toEntity(UsedGoodsFormsRequest request) {
        return UsedGoodsEntity.builder()
            .title(request.getTitle())
            .price(request.getPrice())
            .description(request.getDescription())
            .build();
    }


    public GoodsStatusChangeResponse toResponse(GoodsEntity goodsEntity) {
        return GoodsStatusChangeResponse.builder()
            .goodsId(goodsEntity.getId())
            .goodsStatus(goodsEntity.getStatus())
            .build();
    }

    public List<GoodsStatusChangeResponse> toResponse(List<GoodsEntity> goodsEntityList) {
            return goodsEntityList.stream().map(this::toResponse)
                .collect(Collectors.toList());
    }

    public UsedGoodsFormsResponse toResponse(UsedGoodsEntity usedGoodsEntity) {
        return UsedGoodsFormsResponse.builder()
            .title(usedGoodsEntity.getTitle())
            .price(usedGoodsEntity.getPrice())
            .description(usedGoodsEntity.getDescription())
            .postedAt(usedGoodsEntity.getPostedAt())
            .build();
    }
}
