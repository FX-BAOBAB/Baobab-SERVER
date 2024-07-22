package warehouse.domain.goods.business;

import db.domain.goods.GoodsEntity;
import db.domain.goods.enums.GoodsStatus;
import db.domain.image.ImageEntity;
import db.domain.image.enums.ImageKind;
import global.annotation.Business;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import warehouse.common.error.GoodsErrorCode;
import warehouse.common.exception.Goods.GoodsStrategyException;
import warehouse.domain.goods.controller.enums.GetGoodsStrategy;
import warehouse.domain.goods.controller.model.GoodsResponse;
import warehouse.domain.goods.converter.GoodsConverter;
import warehouse.domain.goods.service.GoodsService;
import warehouse.domain.image.controller.model.ImageListResponse;
import warehouse.domain.image.converter.ImageConverter;
import warehouse.domain.image.service.ImageService;

@Slf4j
@Business
@RequiredArgsConstructor
public class GoodsBusiness {

    private final GoodsConverter goodsConverter;
    private final GoodsService goodsService;
    private final ImageService imageService;
    private final ImageConverter imageConverter;

    public List<List<GoodsResponse>> getGoodsList(GetGoodsStrategy strategy,Long requestId) {

        List<GoodsEntity> goodsList = findGoodsListById(strategy,requestId);

        return getGoodsResponsesBy(goodsList);
    }

    public List<List<GoodsResponse>> getGoodsList(GoodsStatus status) {

        List<GoodsEntity> goodsList = goodsService.findAllByGoodsStatusWithThrow(status);

        return getGoodsResponsesBy(goodsList);
    }

    private List<List<GoodsResponse>> getGoodsResponsesBy(List<GoodsEntity> goodsList) {

        List<List<GoodsResponse>> goodsResponse = new ArrayList<>();

        goodsList.forEach(goodsEntity -> {

            log.info("goodsId = {}", goodsEntity.getId());

            List<ImageEntity> imageList = imageService.getImagesByGoodsId(goodsEntity.getId());

            imageList.forEach(it -> log.info("imageList = {}", it.getId()));

            List<ImageEntity> basicList = imageService.getImageUrlListBy(goodsEntity.getId(),
                ImageKind.BASIC);
            List<ImageEntity> faultList = imageService.getImageUrlListBy(goodsEntity.getId(),
                ImageKind.FAULT);

            ImageListResponse imageListResponse = imageConverter.toImageListResponse(basicList,
                faultList);

            goodsResponse.add(goodsConverter.toResponseListBy(goodsList, imageListResponse));

        });

        return goodsResponse;
    }

    private List<GoodsEntity> findGoodsListById(GetGoodsStrategy strategy, Long requestId) {
        switch (strategy) {
            case RECEIVING -> {
                return goodsService.findAllByReceivingIdWithThrow(requestId);
            }
            case TAKE_BACK -> {
                return goodsService.findAllByTakeBackIdWithThrow(requestId);
            }
            // TODO 출고 완료 후 처리 필요
            //case SHIPPING -> goodsService.findAllByShippingIdWithThrow(requestId);
            default -> throw new GoodsStrategyException(GoodsErrorCode.INVALID_GOODS_STRATEGY);
        }
    }

}
