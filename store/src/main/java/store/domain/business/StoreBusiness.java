package store.domain.business;

import db.domain.goods.GoodsEntity;
import db.domain.goods.enums.GoodsStatus;
import db.domain.receiving.ReceivingEntity;
import db.domain.receiving.enums.ReceivingStatus;
import db.domain.shipping.ShippingEntity;
import db.domain.shipping.enums.ShippingStatus;
import global.annotation.Business;
import java.util.List;
import lombok.RequiredArgsConstructor;
import store.domain.controller.model.GoodsResponse;
import store.domain.controller.model.ReceivingResponse;
import store.domain.controller.model.ShippingResponse;
import store.domain.converter.GoodsConverter;
import store.domain.converter.ReceivingConverter;
import store.domain.converter.ShippingConverter;
import store.domain.service.GoodsService;
import store.domain.service.ImageService;
import store.domain.service.ReceivingService;
import store.domain.service.ShippingService;

@Business
@RequiredArgsConstructor
public class StoreBusiness {

    private final ReceivingService receivingService;
    private final ReceivingConverter receivingConverter;
    private final ShippingService shippingService;
    private final ShippingConverter shippingConverter;
    private final GoodsService goodsService;
    private final GoodsConverter goodsConverter;
    private final ImageService imageService;


    public List<ReceivingResponse> getRequestReceiving(ReceivingStatus status) {
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

    public List<ShippingResponse> getRequestShipping(ShippingStatus status) {
        List<ShippingEntity> shippingEntityList = shippingService.getRequestShippingBy(status);
        return shippingEntityList.stream().map(shippingEntity -> {
            return shippingConverter.toResponse(shippingEntity);
        }).toList();
    }

    public List<GoodsResponse> getGoodsList(GoodsStatus status) {
        List<GoodsEntity> goodsEntityList = goodsService.getGoodsListBy(status);
        return goodsEntityList.stream().map(goodsEntity -> {
            GoodsResponse response = goodsConverter.toResponse(goodsEntity);
            response.setImageIdList(imageService.getImageIdListBy(goodsEntity.getId()));
            return response;
        }).toList();
    }
}
