package warehouse.domain.goods.business;

import db.domain.goods.GoodsEntity;
import db.domain.goods.enums.GoodsStatus;
import db.domain.image.ImageEntity;
import db.domain.image.enums.ImageKind;
import db.domain.receiving.ReceivingEntity;
import db.domain.takeback.TakeBackEntity;
import db.domain.users.UserEntity;
import global.annotation.Business;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import warehouse.common.error.GoodsErrorCode;
import warehouse.common.exception.Goods.GoodsStrategyException;
import warehouse.common.exception.receiving.NotOwnerException;
import warehouse.domain.goods.controller.enums.GetGoodsStrategy;
import warehouse.domain.goods.controller.model.GoodsResponse;
import warehouse.domain.goods.converter.GoodsConverter;
import warehouse.domain.goods.service.GoodsService;
import warehouse.domain.image.controller.model.ImageListResponse;
import warehouse.domain.image.converter.ImageConverter;
import warehouse.domain.image.service.ImageService;
import warehouse.domain.receiving.service.ReceivingService;
import warehouse.domain.takeback.service.TakeBackService;
import warehouse.domain.users.service.UsersService;

@Slf4j
@Business
@RequiredArgsConstructor
public class GoodsBusiness {

    private final GoodsConverter goodsConverter;
    private final GoodsService goodsService;
    private final ImageService imageService;
    private final ImageConverter imageConverter;
    private final UsersService  usersService;
    private final ReceivingService receivingService;
    private final TakeBackService takeBackService;

    public List<GoodsResponse> getGoodsList(GetGoodsStrategy strategy,Long requestId,String email) {

        List<GoodsEntity> goodsList = findGoodsListById(strategy,requestId,email);

        return getGoodsResponsesBy(goodsList,email);
    }

    public List<GoodsResponse> getGoodsList(GoodsStatus status,  String email) {

        List<GoodsEntity> goodsList = goodsService.findAllByGoodsStatusWithThrow(status);

        return getGoodsResponsesBy(goodsList,email);
    }

    private List<GoodsResponse> getGoodsResponsesBy(List<GoodsEntity> goodsList, String email) {

        List<GoodsResponse> goodsResponse = new ArrayList<>();

        goodsList.forEach(goodsEntity -> {

            goodsOwnerCheckWithThrow(email, goodsEntity);

            List<ImageEntity> basicList = imageService.getImageUrlListBy(goodsEntity.getId(),
                ImageKind.BASIC);
            List<ImageEntity> faultList = imageService.getImageUrlListBy(goodsEntity.getId(),
                ImageKind.FAULT);

            ImageListResponse imageListResponse = imageConverter.toImageListResponse(basicList,
                faultList);

            goodsResponse.add(goodsConverter.toResponse(goodsEntity, imageListResponse));

        });

        return goodsResponse;
    }

    private void goodsOwnerCheckWithThrow(String email, GoodsEntity goodsEntity) {
        UserEntity userEntity = usersService.getUserWithThrow(email);
        if (!Objects.equals(userEntity.getId(), goodsEntity.getUserId())) {
            throw new NotOwnerException(GoodsErrorCode.NOT_OWNER);
        }
    }

    private List<GoodsEntity> findGoodsListById(GetGoodsStrategy strategy, Long requestId, String email) {
        switch (strategy) {
            case RECEIVING -> {
                receivingOwnerCheckWithThrow(requestId, email);
                return goodsService.findAllByReceivingIdWithThrow(requestId);
            }
            case TAKE_BACK -> {
                takeBackOwnerCheckWithThrow(requestId, email);
                return goodsService.findAllByTakeBackIdWithThrow(requestId);
            }
            // TODO 출고 완료 후 처리 필요
            //case SHIPPING -> goodsService.findAllByShippingIdWithThrow(requestId);
            default -> throw new GoodsStrategyException(GoodsErrorCode.INVALID_GOODS_STRATEGY);
        }
    }


    private void receivingOwnerCheckWithThrow(Long requestId, String email) {
        UserEntity userEntity = usersService.getUserWithThrow(email);
        ReceivingEntity receivingEntity = receivingService.getReceivingById(requestId);
        if (!Objects.equals(userEntity.getId(), receivingEntity.getUserId())) {
            throw new NotOwnerException(GoodsErrorCode.NOT_OWNER);
        }
    }

    private void takeBackOwnerCheckWithThrow(Long requestId, String email) {
        UserEntity userEntity = usersService.getUserWithThrow(email);
        TakeBackEntity takeBackEntity = takeBackService.getTakeBackById(requestId);
        if (!Objects.equals(userEntity.getId(), takeBackEntity.getUserId())) {
            throw new NotOwnerException(GoodsErrorCode.NOT_OWNER);
        }
    }
}
