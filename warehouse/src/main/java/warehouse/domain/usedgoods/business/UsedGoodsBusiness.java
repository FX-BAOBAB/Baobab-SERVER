package warehouse.domain.usedgoods.business;

import db.domain.goods.GoodsEntity;
import db.domain.goods.enums.GoodsStatus;
import db.domain.image.ImageEntity;
import db.domain.image.enums.ImageKind;
import db.domain.usedgoods.UsedGoodsEntity;
import db.domain.usedgoods.enums.UsedGoodsStatus;
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
import warehouse.domain.usedgoods.controller.model.request.CancelUsedGoodsRequest;
import warehouse.domain.usedgoods.controller.model.request.RegisterUsedGoods;
import warehouse.domain.usedgoods.controller.model.response.MessageResponse;
import warehouse.domain.usedgoods.controller.model.response.UsedGoodsDetailResponse;
import warehouse.domain.usedgoods.converter.UsedGoodsConverter;
import warehouse.domain.usedgoods.service.UsedGoodsService;
import warehouse.domain.users.service.UsersService;

@Business
@RequiredArgsConstructor
@Slf4j
public class UsedGoodsBusiness {

    private final UsedGoodsService usedGoodsService;
    private final GoodsService goodsService;
    private final UsersService usersService;
    private final ImageService imageService;

    private final UsedGoodsConverter usedGoodsConverter;
    private final ImageConverter imageConverter;
    private final GoodsConverter goodsConverter;

    public MessageResponse registerUsedGoods(RegisterUsedGoods request,
        String email) {

        Long userId = usersService.getUserWithThrow(email).getId();

        // GoodsStatus 가 STORAGE 인지 확인
        goodsService.checkStoredGoodsAndStatusWithThrowBy(request.getGoodsId(),
            GoodsStatus.STORAGE);

        GoodsEntity goodsEntity = goodsService.getGoodsById(request.getGoodsId());

        // GoodsStatus 를 USED 로 변경
        goodsService.setGoodsStatusBy(goodsEntity, GoodsStatus.USED);

        UsedGoodsEntity usedGoodsEntity = usedGoodsConverter.toEntity(request);

        // 요청으로 받은 중고 거래 폼을 저장
        usedGoodsService.register(usedGoodsEntity, userId);

        return usedGoodsConverter.toMessageResponse("중고 물품 등록이 완료되었습니다.");
    }

    public MessageResponse cancelUsedGoods(Long usedGoodsId) {

        // 중고로 등록된(REGISTERED) 중고 상품 가져오기
        UsedGoodsEntity usedGoodsEntity = usedGoodsService.getUsedGoodsBy(usedGoodsId,
            UsedGoodsStatus.REGISTERED);

        GoodsEntity goodsEntity = goodsService.getGoodsById(usedGoodsEntity.getGoodsId());

        // goods 상태를 STORAGE(보관) 으로 변경
        goodsService.setGoodsStatusBy(goodsEntity, GoodsStatus.STORAGE);

        // usedGoodsStatus 를 UNREGISTERED 로 변경
        usedGoodsService.setUsedGoodsStatusBy(usedGoodsEntity, UsedGoodsStatus.UNREGISTERED);

        return usedGoodsConverter.toMessageResponse("중고 물품 등록이 취소되었습니다.");
    }

    public MessageResponse cancelUsedGoods(CancelUsedGoodsRequest request) {

        // 중고로 등록된(REGISTERED) 중고 상품 리스트 가져오기
        List<UsedGoodsEntity> usedGoodsEntityList = usedGoodsService.getUsedGoodsListBy(
            request.getUsedGoodsIdList(), UsedGoodsStatus.REGISTERED);

        List<Long> goodsIdList = usedGoodsEntityList.stream()
            .map(usedGoodsEntity -> usedGoodsEntity.getGoodsId()).toList();

        // goodsIdList 로 goods 상태를 STORAGE(보관) 으로 변경
        goodsService.setGoodsStatusBy(goodsIdList, GoodsStatus.STORAGE);

        // usedGoodsStatus 를 갇 로 변경
        usedGoodsService.setUsedGoodsStatusBy(usedGoodsEntityList, UsedGoodsStatus.UNREGISTERED);

        return usedGoodsConverter.toMessageResponse("중고 물품 등록이 취소되었습니다.");
    }

    public UsedGoodsDetailResponse getUsedGoodsDetail(Long usedGoodsId) {

        // 등록되어 있는 UsedGoodsEntity
        UsedGoodsEntity usedGoodsEntity = usedGoodsService.getUsedGoodsBy(usedGoodsId,
            UsedGoodsStatus.REGISTERED);

        GoodsEntity goodsEntity = goodsService.getGoodsById(usedGoodsEntity.getGoodsId());

        GoodsResponse goodsResponse = this.getGoodsResponse(goodsEntity);

        return usedGoodsConverter.toResponse(usedGoodsEntity, goodsResponse);
    }

    //TODO 구매 프로세스 재정립
    /**
     * 구매자의 userId
     * 1. usedGoodsStatus 가 REGISTERED 인지 확인
     * 2. goods 의 userId 를 구매자의 userId 로 변경
     * 3. goods 의 status 를 보관(STORAGE) 로 변경
     * 4. usedGoodsStatus 를 SOLD 로 변경
     */
    public MessageResponse buyUsedGoods(Long usedGoodsId, String email) {

        Long userId = usersService.getUserWithThrow(email).getId();

        UsedGoodsEntity usedGoodsEntity = usedGoodsService.getUsedGoodsBy(usedGoodsId,
            UsedGoodsStatus.REGISTERED); // 1

        GoodsEntity goodsEntity = goodsService.getGoodsById(usedGoodsEntity.getGoodsId());

        goodsService.setUserId(goodsEntity, userId); // 2

        goodsService.setGoodsStatusBy(goodsEntity, GoodsStatus.STORAGE); // 3

        usedGoodsService.setUsedGoodsStatusBy(usedGoodsEntity, UsedGoodsStatus.SOLD); // 4

        return usedGoodsConverter.toMessageResponse("중고 물품 거래가 완료되었습니다.");
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