package warehouse.domain.takeback.business;

import db.domain.goods.GoodsEntity;
import db.domain.image.ImageEntity;
import db.domain.receiving.ReceivingEntity;
import db.domain.takeback.TakeBackEntity;
import global.annotation.Business;
import java.util.List;
import lombok.RequiredArgsConstructor;
import warehouse.domain.goods.controller.model.GoodsResponse;
import warehouse.domain.goods.converter.GoodsConverter;
import warehouse.domain.goods.service.GoodsService;
import warehouse.domain.image.controller.model.ImageList;
import warehouse.domain.image.converter.ImageConverter;
import warehouse.domain.image.service.ImageService;
import warehouse.domain.receiving.controller.model.receiving.ReceivingResponse;
import warehouse.domain.receiving.converter.ReceivingConverter;
import warehouse.domain.receiving.service.ReceivingService;
import warehouse.domain.takeback.controller.model.TakeBackResponse;
import warehouse.domain.takeback.converter.TakeBackConverter;
import warehouse.domain.takeback.service.TakeBackService;

@Business
@RequiredArgsConstructor
public class TakeBackBusiness {

    private final ReceivingService receivingService;
    private final ReceivingConverter receivingConverter;
    private final TakeBackService takeBackService;
    private final TakeBackConverter takeBackConverter;
    private final GoodsService goodsService;
    private final GoodsConverter goodsConverter;
    private final ImageService imageService;
    private final ImageConverter imageConverter;

    public ReceivingResponse takeBackRequest(Long receivingId) {

        ReceivingEntity nonStatusReceivingEntity = receivingService.initReceivingStatus(
            receivingId);
        TakeBackEntity takeBackEntity = takeBackConverter.toEntity(nonStatusReceivingEntity);
        TakeBackEntity newTakeBackEntity = takeBackService.takeBackRequest(takeBackEntity);

        List<GoodsEntity> goodsEntityList = goodsService.findAllByReceivingIdWithThrow(
            receivingId);
        List<ImageEntity> imageEntityList = imageService.getImageEntityListBy(
            goodsEntityList);
        ImageList imageList = imageConverter.toResponseList(imageEntityList);
        List<GoodsResponse> goodsResponseList = goodsConverter.toResponseList(goodsEntityList,
            imageList);

        TakeBackResponse takeResponse = takeBackConverter.toResponse(newTakeBackEntity);

        return receivingConverter.toResponse(nonStatusReceivingEntity, goodsResponseList,
            takeResponse);

    }
}
