package warehouse.domain.takeback.business;

import db.domain.goods.GoodsEntity;
import db.domain.goods.enums.GoodsStatus;
import db.domain.takeback.TakeBackEntity;
import db.domain.users.UserEntity;
import global.annotation.Business;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import warehouse.common.utils.ImageUtils;
import warehouse.domain.goods.controller.model.GoodsResponse;
import warehouse.domain.goods.converter.GoodsConverter;
import warehouse.domain.goods.service.GoodsService;
import warehouse.domain.image.controller.model.ImageListResponse;
import warehouse.domain.image.converter.ImageConverter;
import warehouse.domain.image.service.ImageService;
import warehouse.domain.receiving.converter.receiving.ReceivingConverter;
import warehouse.domain.receiving.service.ReceivingService;
import warehouse.domain.takeback.controller.model.TakeBackListResponse;
import warehouse.domain.takeback.controller.model.TakeBackResponse;
import warehouse.domain.takeback.converter.TakeBackConverter;
import warehouse.domain.takeback.service.TakeBackService;
import warehouse.domain.users.converter.UsersConverter;
import warehouse.domain.users.service.UsersService;

@Slf4j
@Business
@RequiredArgsConstructor
public class TakeBackBusiness {

    private final ReceivingService receivingService;
    private final TakeBackService takeBackService;
    private final TakeBackConverter takeBackConverter;
    private final GoodsService goodsService;
    private final UsersService usersService;
    private final ImageConverter imageConverter;
    private final GoodsConverter goodsConverter;

    public TakeBackResponse takeBackRequest(Long receivingId, String email) {

        List<GoodsEntity> goodsEntityList = goodsService.findAllByReceivingIdWithThrow(receivingId);

        List<Long> goodsIdList = goodsEntityList.stream().map((goodsEntity) -> goodsEntity.getId()).toList();

        goodsService.checkGoodsStatusWithThrow(goodsIdList, GoodsStatus.RECEIVING);

        receivingService.initReceivingStatus(receivingId);

        UserEntity userEntity = usersService.getUserWithThrow(email);

        TakeBackEntity takeBackEntity = takeBackConverter.toEntity(userEntity.getId());

        TakeBackEntity newTakeBackEntity = takeBackService.takeBackRequest(takeBackEntity);

        goodsService.setTakeBackIdAndStatus(goodsEntityList, newTakeBackEntity.getId(),
            GoodsStatus.TAKE_BACK_ING);

        List<GoodsResponse> goodsResponseList = getGoodsResponses(goodsEntityList);

        TakeBackResponse takeResponse = takeBackConverter.toResponse(newTakeBackEntity);

        return takeBackConverter.toResponse(takeResponse, goodsResponseList);
    }

    public TakeBackResponse takeBackRequest(List<Long> goodsIdList, String email) {

        goodsService.checkGoodsStatusWithThrow(goodsIdList, GoodsStatus.RECEIVING);

        UserEntity userEntity = usersService.getUserWithThrow(email);

        TakeBackEntity takeBackEntity = takeBackConverter.toEntity(userEntity.getId());

        TakeBackEntity newTakeBackEntity = takeBackService.takeBackRequest(takeBackEntity);

        List<GoodsEntity> goodsEntityList = goodsService.getGoodsListBy(goodsIdList);

        goodsService.setTakeBackIdAndStatus(goodsEntityList, newTakeBackEntity.getId(),
            GoodsStatus.TAKE_BACK_ING);

        List<GoodsResponse> goodsResponseList = getGoodsResponses(goodsEntityList);

        TakeBackResponse takeResponse = takeBackConverter.toResponse(newTakeBackEntity);

        return takeBackConverter.toResponse(takeResponse, goodsResponseList);
    }

    private List<GoodsResponse> getGoodsResponses(List<GoodsEntity> goodsEntityList) {
        return goodsEntityList.stream().map(goodsEntity -> {
            ImageListResponse imageListResponse = imageConverter.toImageListResponse(goodsEntity);
            return goodsConverter.toResponse(goodsEntity, imageListResponse);
        }).toList();
    }

    public TakeBackListResponse getTakeBackListBy(String email) {
        UserEntity userEntity = usersService.getUserWithThrow(email);

        List<TakeBackEntity> takeBackEntityList = takeBackService.getTakeBackList(userEntity.getId());

        List<TakeBackResponse> takeBackResponseList = takeBackEntityList.stream().map(takeBackEntity -> {
            TakeBackResponse takeBackResponse = takeBackConverter.toResponse(takeBackEntity);

            List<GoodsEntity> goodsList = goodsService.findAllByTakeBackIdWithThrow(
                takeBackEntity.getId());

            List<GoodsResponse> goodsResponseList = goodsList.stream().map(goodsEntity -> {
                ImageListResponse imageListResponse = imageConverter.toImageListResponse(goodsEntity);
                return goodsConverter.toResponse(goodsEntity, imageListResponse);
            }).toList();

            return takeBackConverter.toResponse(takeBackResponse, goodsResponseList);
        }).toList();

        return takeBackConverter.toListResponse(takeBackResponseList);
    }
}
