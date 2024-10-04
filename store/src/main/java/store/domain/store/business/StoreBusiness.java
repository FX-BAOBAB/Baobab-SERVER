package store.domain.store.business;

import db.domain.goods.GoodsEntity;
import db.domain.goods.enums.GoodsStatus;
import db.domain.receiving.ReceivingEntity;
import db.domain.receiving.enums.ReceivingStatus;
import db.domain.store.GoodsLedgerEntity;
import db.domain.store.enums.StoreLocation;
import global.annotation.Business;
import java.util.List;
import lombok.RequiredArgsConstructor;
import store.domain.goods.controller.model.ImageUrlSet;
import store.domain.image.service.ImageService;
import store.domain.goods.controller.model.GoodsResponse;
import store.domain.receiving.controller.model.ReceivingResponse;
import store.domain.store.controller.model.GoodsStoreResponse;
import store.domain.loading.controller.model.StoreRequest;
import store.domain.goods.converter.GoodsConverter;
import store.domain.fault.converter.GoodsLedgerConverter;
import store.domain.receiving.converter.ReceivingConverter;
import store.domain.fault.service.GoodsLedgerService;
import store.domain.goods.service.GoodsService;
import store.domain.receiving.service.ReceivingService;

@Business
@RequiredArgsConstructor
public class StoreBusiness {

    private final ReceivingService receivingService;
    private final ReceivingConverter receivingConverter;
    private final GoodsService goodsService;
    private final GoodsConverter goodsConverter;
    private final GoodsLedgerService goodsLedgerService;
    private final GoodsLedgerConverter goodsLedgerConverter;
    private final ImageService imageService;

    public ReceivingEntity setStore(Long receivingId, StoreRequest request) {
        ReceivingEntity receivingEntity = receivingService.setStatus(receivingId,
            ReceivingStatus.STORAGE);
        List<Long> goodsIds = request.getGoodsIds();
        List<StoreLocation> storeLocation = request.getStoreLocation();
        for (int i = 0; i < goodsIds.size(); i++) {
            goodsService.setStatus(goodsIds.get(i),GoodsStatus.STORAGE);
            GoodsLedgerEntity entity = goodsLedgerConverter.toEntity(goodsIds.get(i),storeLocation.get(i));
            goodsLedgerService.setStore(entity);
        }
        return receivingEntity;
    }

    public List<GoodsStoreResponse> getGoodsStoredListBy(List<Long> goodsIdList) {
        return goodsService.getGoodsListBy(goodsIdList).stream().map(goodsEntity -> {
            GoodsResponse goodsResponse = goodsConverter.toResponse(goodsEntity);
            setImageUrl(goodsEntity, goodsResponse);
            GoodsLedgerEntity entity = goodsLedgerService.getLedgerBy(goodsResponse.getId());
            GoodsStoreResponse response = goodsLedgerConverter.toResponse(entity,goodsResponse);
            return response;
        }).toList();
    }

    private void setImageUrl(GoodsEntity goodsEntity, GoodsResponse response) {
        List<ImageUrlSet> basicImageUrlSet = imageService.getBasicImageUrlSetBy(goodsEntity.getId());
        response.setBasicImageUrlSet(basicImageUrlSet);
        List<ImageUrlSet> faultImageUrlSet = imageService.getFaultImageUrlSetBy(goodsEntity.getId());
        response.setFaultImageUrlSet(faultImageUrlSet);
    }

    public ReceivingResponse getReceivingRequestDetail(Long receivingId) {
        ReceivingEntity receivingEntity = receivingService.getRequestReceivingBy(receivingId);
        ReceivingResponse response = receivingConverter.toResponse(receivingEntity);
        List<GoodsResponse> goodsList = goodsService.getGoodsListBy(receivingId).stream()
            .map(goodsEntity -> {
                GoodsResponse goodsResponse = goodsConverter.toResponse(goodsEntity);
                goodsResponse.setBasicImageUrlSet(imageService.getBasicImageUrlSetBy(goodsEntity.getId()));
                goodsResponse.setFaultImageUrlSet(imageService.getFaultImageUrlSetBy(goodsEntity.getId()));
                return goodsResponse;
            }).toList();
        response.setGoodsList(goodsList);
        return response;
    }
}
