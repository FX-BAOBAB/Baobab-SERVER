package delivery.domain.goods.business;

import db.domain.goods.GoodsEntity;
import db.domain.image.ImageEntity;
import db.domain.image.ImageRepository;
import db.domain.imagemapping.ImageMappingEntity;
import delivery.domain.goods.controller.model.GoodsResponse;
import delivery.domain.goods.controller.model.GoodsResponses;
import delivery.domain.goods.controller.model.ImageSet;
import delivery.domain.goods.converter.GoodsConverter;
import delivery.domain.goods.service.GoodsService;
import delivery.domain.image.converter.ImageConverter;
import delivery.domain.image.service.ImageMappingService;
import delivery.domain.image.service.ImageService;
import global.annotation.Business;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Business
@RequiredArgsConstructor
public class GoodsBusiness {

    private final GoodsService goodsService;
    private final GoodsConverter goodsConverter;
    private final ImageService imageService;

    public GoodsResponses getReceivingGoodsListBy(Long requestId) {

        List<GoodsEntity> goodsEntityList =  goodsService.getReceivingGoodsList(requestId);

        return setImageSet(goodsEntityList);
    }

    public GoodsResponses getShippingGoodsListBy(Long requestId) {

        List<GoodsEntity> goodsEntityList =  goodsService.getShippingGoodsList(requestId);

        return setImageSet(goodsEntityList);
    }

    private GoodsResponses setImageSet(List<GoodsEntity> goodsEntityList) {

        GoodsResponses responses = goodsConverter.toResponseList(goodsEntityList);
        responses.getGoodsResponseList().forEach(response -> {
            response.setBasicImageUrlSet(imageService.getBasicImageUrlSetBy(response.getId()));
            response.setFaultImageUrlSet(imageService.getFaultImageUrlSetBy(response.getId()));
        });

        return responses;
    }

    public GoodsResponse getGoodsBy(Long goodsId) {
        GoodsEntity goodsEntity = goodsService.getGoodsBy(goodsId);
        GoodsResponse response = goodsConverter.toResponse(goodsEntity);
        response.setBasicImageUrlSet(imageService.getBasicImageUrlSetBy(response.getId()));
        response.setFaultImageUrlSet(imageService.getFaultImageUrlSetBy(response.getId()));
        return response;
    }
}
