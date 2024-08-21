package delivery.domain.goods.business;

import db.domain.goods.GoodsEntity;
import db.domain.image.ImageEntity;
import db.domain.image.ImageRepository;
import delivery.domain.goods.controller.model.GoodsResponses;
import delivery.domain.goods.controller.model.ImageSet;
import delivery.domain.goods.converter.GoodsConverter;
import delivery.domain.goods.service.GoodsService;
import delivery.domain.image.converter.ImageConverter;
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
    private final ImageConverter imageConverter;
    private final ImageRepository imageRepository;

    public GoodsResponses getGoodsListBy(Long requestId) {

        List<GoodsEntity> goodsEntityList =  goodsService.getGoodsListBy(requestId);


        GoodsResponses responses = goodsConverter.toResponseList(goodsEntityList);

        responses.getGoodsResponseList().forEach(response -> {
            log.info("goods id [{}] : goods Name [{}] ", response.getId(), response.getName());
            // GoodsImage Set Setting
            List<ImageEntity> imageEntityList = imageService.getImageListBy(response.getId());
            List<ImageSet> imageSet = imageConverter.toImageSetList(imageEntityList);
            response.setImages(imageSet);
        });

        return responses;
    }
}
