package warehouse.domain.usedgoods.converter;

import db.domain.usedgoods.EntitySearchCondition;
import db.domain.usedgoods.UsedGoodsEntity;
import global.annotation.Converter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;
import warehouse.domain.goods.controller.model.GoodsResponse;
import warehouse.domain.usedgoods.controller.model.request.SearchCondition;
import warehouse.domain.usedgoods.controller.model.request.RegisterUsedGoods;
import warehouse.domain.usedgoods.controller.model.response.MessageResponse;
import warehouse.domain.usedgoods.controller.model.response.UsedGoodsDetailResponse;
import warehouse.domain.usedgoods.controller.model.response.UsedGoodsSearchResponse;

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

    public UsedGoodsDetailResponse toResponse(UsedGoodsEntity usedGoodsEntity,
        GoodsResponse goodsResponse) {
        return UsedGoodsDetailResponse.builder()
            .title(usedGoodsEntity.getTitle())
            .price(usedGoodsEntity.getPrice())
            .description(usedGoodsEntity.getDescription())
            .postedAt(usedGoodsEntity.getPostedAt())
            .goods(goodsResponse)
            .build();
    }

    public UsedGoodsSearchResponse toSearchResponse(UsedGoodsEntity usedGoodsEntity,
        GoodsResponse goodsResponse) {
        return UsedGoodsSearchResponse.builder()
            .usedGoodsId(usedGoodsEntity.getId())
            .title(usedGoodsEntity.getTitle())
            .price(usedGoodsEntity.getPrice())
            .postedAt(usedGoodsEntity.getPostedAt())
            .status(usedGoodsEntity.getStatus())
            .goods(goodsResponse)
            .build();
    }

    public MessageResponse toMessageResponse(String message) {
        return MessageResponse.builder()
            .message(message)
            .build();
    }

    public EntitySearchCondition toEntitySearchCondition(SearchCondition condition, Pageable page,
        Long userId) {
        return EntitySearchCondition.builder()
            .usedGoodsId(condition.getUsedGoodsId())
            .keyword(condition.getKeyword())
            .minPrice(condition.getMinPrice())
            .maxPrice(condition.getMaxPrice())
            .startDate(condition.getStartDate())
            .endDate(condition.getEndDate())
            .page(page)
            .userId(userId)
            .build();
    }

}
