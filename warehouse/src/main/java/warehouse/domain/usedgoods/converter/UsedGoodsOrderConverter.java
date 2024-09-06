package warehouse.domain.usedgoods.converter;

import db.domain.usedgoods.UsedGoodsEntity;
import db.domain.usedgoods.enums.UsedGoodsStatus;
import db.domain.usedgoodsorder.UsedGoodsOrderEntity;
import global.annotation.Converter;
import java.time.LocalDateTime;
import java.util.List;
import warehouse.domain.usedgoods.controller.model.response.UsedGoodsOrderResponse;
import warehouse.domain.usedgoods.controller.model.response.UsedGoodsStatusResponse;

@Converter
public class UsedGoodsOrderConverter {

    public UsedGoodsOrderEntity toEntity(UsedGoodsEntity usedGoodsEntity, Long userId) {
        return UsedGoodsOrderEntity.builder()
            .sellerId(usedGoodsEntity.getUserId())
            .buyerId(userId)
            .usedGoodsId(usedGoodsEntity.getId())
            .createdAt(LocalDateTime.now())
            .build();
    }

    public UsedGoodsOrderResponse toResponse(UsedGoodsOrderEntity orderEntity) {
        return UsedGoodsOrderResponse.builder()
            .usedGoodsOrderId(orderEntity.getId())
            .sellerId(orderEntity.getSellerId())
            .buyerId(orderEntity.getBuyerId())
            .createdAt(orderEntity.getCreatedAt())
            .usedGoodsId(orderEntity.getUsedGoodsId())
            .build();
    }

    public List<UsedGoodsOrderResponse> toResponse(List<UsedGoodsOrderEntity> orderEntityList) {
        return orderEntityList.stream()
            .map(orderEntity -> this.toResponse(orderEntity)).toList();
    }

    public UsedGoodsStatusResponse toResponse(UsedGoodsStatus usedGoodsStatus) {
        return UsedGoodsStatusResponse.builder()
            .usedGoodsStatus(usedGoodsStatus)
            .build();
    }

}