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
import warehouse.common.utils.ImageUtils;
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
    private final UsedGoodsConverter usedGoodsConverter;
    private final ImageConverter imageConverter;
    private final GoodsConverter goodsConverter;

    public TransformUsedGoodsResponse transformUsedGoods(Long goodsId) {

        // GoodsStatus 를 USED 로 변경
        setGoodsStatusUsedBy(goodsId, GoodsStatus.USED);

        GoodsEntity goodsEntity = getGoodsBy(goodsId);

        return usedGoodsConverter.toResponse(goodsEntity);
    }

    public List<TransformUsedGoodsResponse> transformUsedGoods(List<Long> goodsIdList) {
        return goodsIdList.stream().map(goodsId -> transformUsedGoods(goodsId)).toList();
    }

    public TransformUsedGoodsResponse cancelUsedGoodsRequest(Long goodsId) {

        goodsService.checkGoodsStatusWithThrow(goodsId, GoodsStatus.USED);

        setGoodsStatusUsedBy(goodsId, GoodsStatus.STORAGE);

        GoodsEntity goodsEntity = getGoodsBy(goodsId);

        return usedGoodsConverter.toResponse(goodsEntity);
    }

    public List<TransformUsedGoodsResponse> cancelUsedGoodsRequest(List<Long> goodsIdList) {

        goodsService.checkGoodsStatusWithThrow(goodsIdList, GoodsStatus.USED);

        goodsIdList.forEach(goodsId -> setGoodsStatusUsedBy(goodsId, GoodsStatus.STORAGE));

        List<GoodsEntity> goodsEntityList = goodsService.getGoodsListBy(goodsIdList);

        return usedGoodsConverter.toResponse(goodsEntityList);
    }

    public UsedGoodsPostResponse post(UsedGoodsPostRequest request, String email) {

        goodsService.checkGoodsStatusWithThrow(request.getGoodsId(), GoodsStatus.USED);

        Long userId = usersService.getUserWithThrow(email).getId();

        UsedGoodsEntity usedGoodsEntity = usedGoodsConverter.toEntity(request, userId);

        UsedGoodsEntity savedEntity = usedGoodsService.post(usedGoodsEntity);

        GoodsEntity goodsEntity = getGoodsBy(request.getGoodsId());

        ImageListResponse imageListResponse = imageConverter.toImageListResponse(goodsEntity);

        GoodsResponse goodsResponse = goodsConverter.toResponse(goodsEntity, imageListResponse);

        return usedGoodsConverter.toResponse(savedEntity, goodsResponse);
    }

    private void setGoodsStatusUsedBy(Long goodId, GoodsStatus status) {
        goodsService.setGoodsStatusBy(goodId, status);
    }

    private GoodsEntity getGoodsBy(Long goodsId) {
        return goodsService.getGoodsBy(goodsId);
    }

}