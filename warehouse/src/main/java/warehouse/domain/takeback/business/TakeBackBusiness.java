package warehouse.domain.takeback.business;

import db.domain.goods.GoodsEntity;
import db.domain.goods.enums.GoodsStatus;
import db.domain.image.ImageEntity;
import db.domain.image.enums.ImageKind;
import db.domain.receiving.ReceivingEntity;
import db.domain.receiving.enums.ReceivingStatus;
import db.domain.takeback.TakeBackEntity;
import global.annotation.Business;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import warehouse.domain.goods.controller.model.GoodsResponse;
import warehouse.domain.goods.converter.GoodsConverter;
import warehouse.domain.goods.service.GoodsService;
import warehouse.domain.image.controller.model.ImageListResponse;
import warehouse.domain.image.converter.ImageConverter;
import warehouse.domain.image.service.ImageService;
import warehouse.domain.receiving.converter.receiving.ReceivingConverter;
import warehouse.domain.receiving.service.ReceivingService;
import warehouse.domain.takeback.controller.model.TakeBackResponse;
import warehouse.domain.takeback.converter.TakeBackConverter;
import warehouse.domain.takeback.service.TakeBackService;

@Slf4j
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

    public TakeBackResponse takeBackRequest(Long receivingId) {

        goodsService.validateTakeBackStatusWithThrow(receivingId);

        receivingService.initReceivingStatus(receivingId); // CLOSE

        TakeBackEntity takeBackEntity = takeBackConverter.toEntity();

        TakeBackEntity newTakeBackEntity = takeBackService.takeBackRequest(takeBackEntity);

        List<GoodsEntity> goodsEntityList = goodsService.findAllByReceivingIdWithThrow(
            receivingId);

        goodsService.setTakeBackIdAndStatus(goodsEntityList, newTakeBackEntity.getId(),
            GoodsStatus.TAKE_BACK_ING);

        List<GoodsResponse> goodsResponseList = getGoodsResponses(
            goodsEntityList);

        TakeBackResponse takeResponse = takeBackConverter.toResponse(newTakeBackEntity);

        return takeBackConverter.toResponse(takeResponse, goodsResponseList);
    }

    public TakeBackResponse takeBackRequest(List<Long> goodsIdList) {

        goodsService.validateTakeBackStatusWithThrow(goodsIdList);

        TakeBackEntity takeBackEntity = takeBackConverter.toEntity();

        TakeBackEntity newTakeBackEntity = takeBackService.takeBackRequest(takeBackEntity);

        List<GoodsEntity> goodsEntityList = goodsService.getGoodsListBy(goodsIdList);

        goodsService.setTakeBackIdAndStatus(goodsEntityList, newTakeBackEntity.getId(),
            GoodsStatus.TAKE_BACK_ING);

        List<GoodsResponse> goodsResponseList = getGoodsResponses(
            goodsEntityList);

        TakeBackResponse takeResponse = takeBackConverter.toResponse(newTakeBackEntity);

        return takeBackConverter.toResponse(takeResponse, goodsResponseList);
    }

    private List<GoodsResponse> getGoodsResponses(List<GoodsEntity> goodsEntityList) {
        return goodsEntityList.stream().map(it -> {
            List<ImageEntity> basicImageEntityList = imageService.getImageUrlListBy(it.getId(),
                ImageKind.BASIC);
            List<ImageEntity> faultImageEntityList = imageService.getImageUrlListBy(it.getId(),
                ImageKind.FAULT);
            ImageListResponse imageListResponse = imageConverter.toImageListResponse(
                basicImageEntityList, faultImageEntityList);
            return goodsConverter.toResponse(it, imageListResponse);
        }).toList();
    }
}
