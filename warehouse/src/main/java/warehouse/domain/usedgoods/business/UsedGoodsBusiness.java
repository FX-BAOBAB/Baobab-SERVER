package warehouse.domain.usedgoods.business;

import db.domain.goods.GoodsEntity;
import db.domain.goods.enums.GoodsStatus;
import db.domain.image.ImageEntity;
import db.domain.image.enums.ImageKind;
import db.domain.usedgoods.UsedGoodsEntity;
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
import warehouse.domain.usedgoods.controller.model.request.UsedGoodsPostRequest;
import warehouse.domain.usedgoods.controller.model.response.TransformUsedGoodsResponse;
import warehouse.domain.usedgoods.controller.model.response.UsedGoodsPostResponse;
import warehouse.domain.usedgoods.converter.UsedGoodsConverter;
import warehouse.domain.usedgoods.service.UsedGoodsService;
import warehouse.domain.users.service.UsersService;

@Business
@RequiredArgsConstructor
@Slf4j
public class UsedGoodsBusiness {

    private final GoodsService goodsService;
    private final UsersService usersService;
    private final UsedGoodsService usedGoodsService;
    private final ImageService imageService;

    private final UsedGoodsConverter usedGoodsConverter;
    private final ImageConverter imageConverter;
    private final GoodsConverter goodsConverter;

    public TransformUsedGoodsResponse transformUsedGoods(Long goodsId) {

        // GoodsStatus 가 STORAGE 가 아닌 경우 예외
        goodsService.checkStoredGoodsAndStatusWithThrowBy(List.of(goodsId), GoodsStatus.STORAGE);

        // GoodsStatus 를 USED 로 변경
        goodsService.setGoodsStatusBy(List.of(goodsId), GoodsStatus.USED);

        GoodsEntity goodsEntity = goodsService.getGoodsListBy(goodsId);

        return usedGoodsConverter.toResponse(goodsEntity);
    }

    public List<TransformUsedGoodsResponse> transformUsedGoods(List<Long> goodsIdList) {

        goodsService.checkStoredGoodsAndStatusWithThrowBy(goodsIdList, GoodsStatus.STORAGE);

        goodsService.setGoodsStatusBy(goodsIdList, GoodsStatus.USED);

        List<GoodsEntity> goodsEntityList = goodsService.getGoodsListBy(goodsIdList);

        return usedGoodsConverter.toResponse(goodsEntityList);
    }

    public TransformUsedGoodsResponse cancelUsedGoodsRequest(Long goodsId) {

        goodsService.checkStoredGoodsAndStatusWithThrowBy(goodsId, GoodsStatus.USED);

        goodsService.setGoodsStatusBy(List.of(goodsId), GoodsStatus.STORAGE);

        GoodsEntity goodsEntity = goodsService.getGoodsListBy(goodsId);

        return usedGoodsConverter.toResponse(goodsEntity);
    }

    public List<TransformUsedGoodsResponse> cancelUsedGoodsRequest(List<Long> goodsIdList) {

        goodsService.checkStoredGoodsAndStatusWithThrowBy(goodsIdList, GoodsStatus.USED);

        goodsService.setGoodsStatusBy(goodsIdList, GoodsStatus.STORAGE);

        List<GoodsEntity> goodsEntityList = goodsService.getGoodsListBy(goodsIdList);

        return usedGoodsConverter.toResponse(goodsEntityList);
    }

    public UsedGoodsPostResponse post(UsedGoodsPostRequest request, String email) {

        goodsService.checkStoredGoodsAndStatusWithThrowBy(request.getGoodsId(), GoodsStatus.USED);

        Long userId = usersService.getUserWithThrow(email).getId();

        UsedGoodsEntity usedGoodsEntity = usedGoodsConverter.toEntity(request);

        UsedGoodsEntity savedEntity = usedGoodsService.post(usedGoodsEntity, userId);

        GoodsEntity goodsEntity = goodsService.getGoodsListBy(request.getGoodsId());

        GoodsResponse goodsResponse = getGoodsResponse(
            goodsEntity);

        return usedGoodsConverter.toResponse(savedEntity, goodsResponse);
    }


    private GoodsResponse getGoodsResponse(GoodsEntity goodsEntity) {
        List<ImageEntity> basicImageEntityList = imageService.getImageUrlListBy(goodsEntity.getId(),
            ImageKind.BASIC);
        List<ImageEntity> faultImageEntityList = imageService.getImageUrlListBy(goodsEntity.getId(),
            ImageKind.FAULT);
        ImageListResponse imageListResponse = imageConverter.toImageListResponse(
            basicImageEntityList, faultImageEntityList);
        return goodsConverter.toResponse(goodsEntity, imageListResponse);
    }

}