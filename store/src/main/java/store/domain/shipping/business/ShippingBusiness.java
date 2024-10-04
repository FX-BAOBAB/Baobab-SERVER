package store.domain.shipping.business;

import db.domain.shipping.ShippingEntity;
import db.domain.shipping.enums.ShippingStatus;
import global.annotation.Business;
import java.util.List;
import lombok.RequiredArgsConstructor;
import store.domain.goods.controller.model.GoodsResponse;
import store.domain.image.service.ImageService;
import store.domain.shipping.controller.model.ShippingResponse;
import store.domain.goods.converter.GoodsConverter;
import store.domain.shipping.converter.ShippingConverter;
import store.domain.goods.service.GoodsService;
import store.domain.shipping.service.ShippingService;

@Business
@RequiredArgsConstructor
public class ShippingBusiness {

    private final ShippingService shippingService;
    private final ShippingConverter shippingConverter;
    private final GoodsService goodsService;
    private final GoodsConverter goodsConverter;
    private final ImageService imageService;

    public List<ShippingResponse> getRequestShipping(ShippingStatus status) {
        if (status == null){
            status = ShippingStatus.PENDING;
        }
        List<ShippingEntity> shippingEntityList = shippingService.getRequestShippingBy(status);
        return shippingEntityList.stream().map(shippingEntity -> {
            return shippingConverter.toResponse(shippingEntity);
        }).toList();
    }

    public ShippingResponse getShippingRequestDetail(Long shippingId) {
        ShippingEntity shippingEntity = shippingService.getRequestShippingBy(shippingId);
        return getShippingResponse(shippingEntity);
    }

    private ShippingResponse getShippingResponse(ShippingEntity shippingEntity) {
        ShippingResponse response = shippingConverter.toResponse(shippingEntity);
        List<GoodsResponse> goodsList = goodsService.getShippingGoodsListBy(shippingEntity.getId()).stream()
            .map(goodsEntity -> {
                GoodsResponse goodsResponse = goodsConverter.toResponse(goodsEntity);
                goodsResponse.setBasicImageUrlSet(imageService.getBasicImageUrlSetBy(goodsEntity.getId()));
                goodsResponse.setFaultImageUrlSet(imageService.getFaultImageUrlSetBy(goodsEntity.getId()));
                return goodsResponse;
            }).toList();
        response.setGoodsList(goodsList);
        return response;
    }

    public ShippingResponse readyShipping(Long shippingId) {
        ShippingEntity shippingEntity =  shippingService.setStatus(shippingId,ShippingStatus.READY);
        return getShippingResponse(shippingEntity);
    }

}
