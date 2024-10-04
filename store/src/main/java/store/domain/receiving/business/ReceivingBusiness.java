package store.domain.receiving.business;

import db.domain.receiving.ReceivingEntity;
import db.domain.receiving.enums.ReceivingStatus;
import global.annotation.Business;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import store.domain.goods.controller.model.GoodsResponse;
import store.domain.image.service.ImageService;
import store.domain.receiving.controller.model.ReceivingResponse;
import store.domain.goods.converter.GoodsConverter;
import store.domain.receiving.converter.ReceivingConverter;
import store.domain.goods.service.GoodsService;
import store.domain.receiving.service.ReceivingService;

@Slf4j
@Business
@RequiredArgsConstructor
public class ReceivingBusiness {

    private final ReceivingService receivingService;
    private final ReceivingConverter receivingConverter;
    private final GoodsService goodsService;
    private final GoodsConverter goodsConverter;
    private final ImageService imageService;

    public List<ReceivingResponse> getRequestReceiving(ReceivingStatus status) {

        if (status == null){
            status = ReceivingStatus.RECEIVING;
        }

        List<ReceivingEntity> receivingEntityList = receivingService.getRequestReceivingBy(status);

        return receivingEntityList.stream().map(receivingEntity -> {
            return receivingConverter.toResponse(receivingEntity);
        }).toList();

    }

    public List<ReceivingResponse> getRequestReceiving() {
        List<ReceivingEntity> receivingEntityList = receivingService.getRequestReceiving();
        return receivingEntityList.stream().map(receivingEntity -> {
            return receivingConverter.toResponse(receivingEntity);
        }).toList();
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
