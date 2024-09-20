package store.domain.management.converter;

import db.domain.store.GoodsLedgerEntity;
import db.domain.store.enums.StoreLocation;
import global.annotation.Converter;
import java.time.LocalDateTime;
import store.domain.management.controller.model.GoodsResponse;
import store.domain.management.controller.model.GoodsStoreResponse;
import store.domain.management.controller.model.StoreRequest;

@Converter
public class GoodsLedgerConverter {


    public GoodsLedgerEntity toEntity(Long goodsId, StoreLocation location) {
        return GoodsLedgerEntity.builder()
            .goodsId(goodsId)
            .location(location)
            .storedDate(LocalDateTime.now())
            .build();
    }

    public GoodsStoreResponse toResponse(GoodsLedgerEntity entity, GoodsResponse goodsResponse) {
        return GoodsStoreResponse.builder()
            .id(goodsResponse.getId())
            .name(goodsResponse.getName())
            .modelName(goodsResponse.getModelName())
            .status(goodsResponse.getStatus())
            .quantity(goodsResponse.getQuantity())
            .category(goodsResponse.getCategory())
            .abandonmentAt(goodsResponse.getAbandonmentAt())
            .basicImageUrlSet(goodsResponse.getBasicImageUrlSet())
            .faultImageUrlSet(goodsResponse.getFaultImageUrlSet())
            .storeLocation(entity.getLocation())
            .storedAt(entity.getStoredDate())
            .build();
    }
}
