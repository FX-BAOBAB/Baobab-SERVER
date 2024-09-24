package store.domain.goods.business;

import db.domain.goods.GoodsEntity;
import db.domain.goods.GoodsRepository;
import db.domain.goods.enums.GoodsStatus;
import global.annotation.Business;
import java.util.List;
import lombok.RequiredArgsConstructor;
import store.domain.goods.controller.model.GoodsResponse;
import store.domain.goods.controller.model.ImageUrlSet;
import store.domain.goods.converter.GoodsConverter;
import store.domain.goods.service.GoodsService;
import store.domain.image.domain.image.converter.ImageConverter;
import store.domain.image.domain.image.service.ImageService;

@Business
@RequiredArgsConstructor
public class GoodsBusiness {

    private final GoodsService goodsService;
    private final GoodsConverter goodsConverter;
    private final ImageService imageService;
    private final ImageConverter imageConverter;

    public List<GoodsResponse> getGoodsList(GoodsStatus status) {
        List<GoodsEntity> goodsEntityList = goodsService.getGoodsListBy(status);
        return goodsEntityList.stream().map(goodsEntity -> {
            GoodsResponse response = goodsConverter.toResponse(goodsEntity);
            setImageUrl(goodsEntity, response);
            return response;
        }).toList();
    }

    public List<GoodsResponse> getGoodsListBy(List<Long> goodsIdList) {
        return goodsService.getGoodsListBy(goodsIdList).stream().map(goodsEntity -> {
            GoodsResponse response = goodsConverter.toResponse(goodsEntity);
            setImageUrl(goodsEntity, response);
            return response;
        }).toList();

    }

    // TODO Exception 처리 필요
    public GoodsResponse getGoodsBy(Long goodsId) {
        GoodsEntity goodsEntity = goodsService.getGoodsBy(goodsId)
            .orElseThrow(() -> new RuntimeException("물품이 존재하지 않습니다."));
        GoodsResponse response = goodsConverter.toResponse(goodsEntity);
        setImageUrl(goodsEntity, response);
        return response;
    }

    private void setImageUrl(GoodsEntity goodsEntity, GoodsResponse response) {
        List<ImageUrlSet> basicImageUrlSet = imageService.getBasicImageUrlSetBy(goodsEntity.getId());
        response.setBasicImageUrlSet(basicImageUrlSet);
        List<ImageUrlSet> faultImageUrlSet = imageService.getFaultImageUrlSetBy(goodsEntity.getId());
        response.setFaultImageUrlSet(faultImageUrlSet);
    }

}
