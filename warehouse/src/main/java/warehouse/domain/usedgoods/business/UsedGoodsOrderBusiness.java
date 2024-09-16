package warehouse.domain.usedgoods.business;

import db.domain.goods.GoodsEntity;
import db.domain.goods.enums.GoodsStatus;
import db.domain.usedgoods.UsedGoodsEntity;
import db.domain.usedgoods.enums.UsedGoodsStatus;
import db.domain.usedgoodsorder.UsedGoodsOrderEntity;
import global.annotation.Business;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import warehouse.common.error.UsedGoodsErrorCode;
import warehouse.common.exception.usedGoods.ExistingOrderException;
import warehouse.common.exception.usedGoods.InvalidOrderAuthorityException;
import warehouse.domain.goods.controller.model.GoodsResponse;
import warehouse.domain.goods.converter.GoodsConverter;
import warehouse.domain.goods.service.GoodsService;
import warehouse.domain.image.controller.model.ImageListResponse;
import warehouse.domain.image.converter.ImageConverter;
import warehouse.domain.usedgoods.controller.model.response.UsedGoodsOrderResponse;
import warehouse.domain.usedgoods.controller.model.response.UsedGoodsSearchResponse;
import warehouse.domain.usedgoods.converter.UsedGoodsConverter;
import warehouse.domain.usedgoods.converter.UsedGoodsOrderConverter;
import warehouse.domain.usedgoods.service.UsedGoodsOrderService;
import warehouse.domain.usedgoods.service.UsedGoodsService;
import warehouse.domain.users.security.service.UsersService;


@Business
@RequiredArgsConstructor
@Slf4j
public class UsedGoodsOrderBusiness {

    private final UsersService usersService;
    private final UsedGoodsOrderService usedGoodsOrderService;
    private final UsedGoodsService usedGoodsService;
    private final UsedGoodsOrderConverter usedGoodsOrderConverter;
    private final GoodsService goodsService;
    private final ImageConverter imageConverter;
    private final GoodsConverter goodsConverter;
    private final UsedGoodsConverter usedGoodsConverter;


    public UsedGoodsOrderResponse orderUsedGoods(Long usedGoodsId, String email) {

        // 1. 구매자의 userId
        Long buyerId = getUserId(email);

        // 2. UsedGoods 조회
        UsedGoodsEntity usedGoodsEntity = usedGoodsService.getUsedGoodsBy(usedGoodsId);

        // 3. 자신이 올린 물품을 구매하려는 경우 방지
        if (usedGoodsEntity.getUserId().equals(buyerId)) {
            throw new InvalidOrderAuthorityException(UsedGoodsErrorCode.INVALID_ORDER_AUTHORITY);
        }

        // 4. 구매자가 이미 거래 요청을 했는지 확인
        if (usedGoodsOrderService.hasExistingOrder(usedGoodsId, buyerId)) {
            throw new ExistingOrderException(UsedGoodsErrorCode.EXISTING_ORDER);
        }

        UsedGoodsOrderEntity orderEntity = usedGoodsOrderConverter.toEntity(usedGoodsEntity,
            buyerId);

        // 5. 새로운 구매 요청서 생성
        UsedGoodsOrderEntity savedOrderEntity = usedGoodsOrderService.orderUsedGoods(orderEntity);

        // 6. UsedGoodsStatus = SOLD 로 변경
        setUsedGoodsStatusBy(usedGoodsEntity, UsedGoodsStatus.SOLD);

        // 7. GoodsStatus = STORAGE 로 변경
        GoodsEntity goodsEntity = goodsService.getGoodsBy(usedGoodsEntity.getGoodsId());
        setGoodsStatusBy(goodsEntity.getId(), GoodsStatus.STORAGE);

        // 8. 물품 소유권을 구매자로 변경
        goodsService.setUserId(goodsEntity, buyerId);

        return usedGoodsOrderConverter.toResponse(savedOrderEntity);
    }


    public List<UsedGoodsOrderResponse> getSellerOrderRequest(String email) {
        Long userId = getUserId(email);
        List<UsedGoodsOrderEntity> usedGoodsOrderList = usedGoodsOrderService.getUsedGoodsOrderListBySellerId(
            userId);
        return usedGoodsOrderConverter.toResponse(usedGoodsOrderList);
    }

    public List<UsedGoodsOrderResponse> getBuyerOrderRequest(String email) {
        Long userId = getUserId(email);
        List<UsedGoodsOrderEntity> usedGoodsOrderList = usedGoodsOrderService.getUsedGoodsOrderListByBuyerId(
            userId);
        return usedGoodsOrderConverter.toResponse(usedGoodsOrderList);
    }

    public UsedGoodsOrderResponse getOrderRequest(Long usedGoodsId) {
        UsedGoodsOrderEntity usedGoodsOrderEntity = usedGoodsOrderService.getUsedGoodsOrderBy(
            usedGoodsId);
        return usedGoodsOrderConverter.toResponse(usedGoodsOrderEntity);
    }

    private Long getUserId(String email) {
        return usersService.getUserWithThrow(email).getId();
    }

    private void setUsedGoodsStatusBy(UsedGoodsEntity usedGoodsEntity, UsedGoodsStatus status) {
        usedGoodsService.setUsedGoodsStatusBy(usedGoodsEntity, status);
    }

    private void setGoodsStatusBy(Long goodsId, GoodsStatus status) {
        goodsService.setGoodsStatusBy(goodsId, status);
    }


    public List<UsedGoodsSearchResponse> getPurchasedGoodsList(String email) {

        Long userId = usersService.getUserWithThrow(email).getId();

        List<Long> usedGoodsIdList = usedGoodsOrderService.getUsedGoodsOrderListByBuyerId(userId)
            .stream()
            .map(usedGoodsOrderEntity ->
                usedGoodsOrderEntity.getUsedGoodsId()
            ).toList();

        List<UsedGoodsEntity> usedGoodsEntityList = usedGoodsService.getUsedGoodsListBy(
            usedGoodsIdList, UsedGoodsStatus.SOLD);

        Map<Long, GoodsEntity> goodsEntityMap = getGoodsEntityMap(usedGoodsEntityList);

        return usedGoodsEntityList.stream().map(usedGoodsEntity -> {
            Long goodsId = usedGoodsEntity.getGoodsId();
            GoodsEntity goodsEntity = goodsEntityMap.get(goodsId);
            ImageListResponse imageListResponse = imageConverter.toImageListResponse(goodsEntity);
            GoodsResponse response = goodsConverter.toResponse(goodsEntity, imageListResponse);
            return usedGoodsConverter.toSearchResponse(usedGoodsEntity, response);
        }).toList();

    }

    private Map<Long, GoodsEntity> getGoodsEntityMap(List<UsedGoodsEntity> usedGoodsEntityList) {
        List<Long> goodsIdList = usedGoodsEntityList.stream()
            .map(usedGoodsEntity -> usedGoodsEntity.getGoodsId()).toList();
        List<GoodsEntity> goodsEntityList = goodsService.getGoodsListBy(goodsIdList);
        Map<Long, GoodsEntity> goodsEntityMap = goodsEntityList.stream().collect(Collectors.toMap(
            goodsEntity -> goodsEntity.getId(), // key
            goodsEntity -> goodsEntity // value
        ));
        return goodsEntityMap;
    }

}