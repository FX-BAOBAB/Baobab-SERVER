package warehouse.domain.usedgoods.business;

import db.domain.goods.GoodsEntity;
import db.domain.goods.enums.GoodsStatus;
import db.domain.usedgoods.EntitySearchCondition;
import db.domain.usedgoods.UsedGoodsEntity;
import db.domain.usedgoods.enums.UsedGoodsStatus;
import db.domain.usedgoodsorder.UsedGoodsOrderEntity;
import db.domain.usedgoodsorder.enums.UsedGoodsOrderStatus;
import global.annotation.Business;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import warehouse.common.error.UsedGoodsErrorCode;
import warehouse.common.exception.usedGoods.ApprovedOrderException;
import warehouse.common.exception.usedGoods.ExistingOrderException;
import warehouse.common.exception.usedGoods.InvalidOrderAuthorityException;
import warehouse.domain.goods.controller.model.GoodsResponse;
import warehouse.domain.goods.converter.GoodsConverter;
import warehouse.domain.goods.service.GoodsService;
import warehouse.domain.image.controller.model.ImageListResponse;
import warehouse.domain.image.converter.ImageConverter;
import warehouse.domain.usedgoods.controller.model.request.SearchCondition;
import warehouse.domain.usedgoods.controller.model.request.CancelUsedGoodsRequest;
import warehouse.domain.usedgoods.controller.model.request.RegisterUsedGoods;
import warehouse.domain.usedgoods.controller.model.response.MessageResponse;
import warehouse.domain.usedgoods.controller.model.response.UsedGoodsDetailResponse;
import warehouse.domain.usedgoods.controller.model.response.UsedGoodsOrderResponse;
import warehouse.domain.usedgoods.controller.model.response.UsedGoodsSearchResponse;
import warehouse.domain.usedgoods.controller.model.response.UsedGoodsStatusResponse;
import warehouse.domain.usedgoods.converter.UsedGoodsConverter;
import warehouse.domain.usedgoods.converter.UsedGoodsOrderConverter;
import warehouse.domain.usedgoods.service.UsedGoodsService;
import warehouse.domain.users.service.UsersService;

@Business
@RequiredArgsConstructor
@Slf4j
public class UsedGoodsBusiness {

    private final UsedGoodsService usedGoodsService;
    private final GoodsService goodsService;
    private final UsersService usersService;

    private final UsedGoodsConverter usedGoodsConverter;
    private final ImageConverter imageConverter;
    private final GoodsConverter goodsConverter;
    private final UsedGoodsOrderConverter usedGoodsOrderConverter;

    public MessageResponse registerUsedGoods(RegisterUsedGoods request,
        String email) {

        Long userId = getUserId(email);

        // GoodsStatus 가 STORAGE 인지 확인
        goodsService.checkGoodsStatusWithThrow(request.getGoodsId(), GoodsStatus.STORAGE);

        GoodsEntity goodsEntity = getGoodsBy(request.getGoodsId());

        // GoodsStatus 를 USED 로 변경
        setGoodsStatusBy(goodsEntity.getId(), GoodsStatus.USED);

        UsedGoodsEntity usedGoodsEntity = usedGoodsConverter.toEntity(request, userId);

        // 요청으로 받은 중고 거래 폼을 저장
        usedGoodsService.register(usedGoodsEntity);

        return usedGoodsConverter.toMessageResponse("중고 물품 등록이 완료되었습니다.");
    }

    public MessageResponse cancelUsedGoods(Long usedGoodsId) {

        // 중고로 등록된(REGISTERED) 중고 상품 가져오기
        UsedGoodsEntity usedGoodsEntity = usedGoodsService.getUsedGoodsBy(usedGoodsId,
            UsedGoodsStatus.REGISTERED);

        GoodsEntity goodsEntity = getGoodsBy(usedGoodsEntity.getGoodsId());

        // goods 상태를 STORAGE(보관) 으로 변경
        setGoodsStatusBy(goodsEntity.getId(), GoodsStatus.STORAGE);

        // usedGoodsStatus 를 UNREGISTERED 로 변경
        setUsedGoodsStatusBy(usedGoodsEntity, UsedGoodsStatus.UNREGISTERED);

        return usedGoodsConverter.toMessageResponse("중고 물품 등록이 취소되었습니다.");
    }

    public MessageResponse cancelUsedGoods(CancelUsedGoodsRequest request) {

        // 중고로 등록된(REGISTERED) 중고 상품 리스트 가져오기
        List<UsedGoodsEntity> usedGoodsEntityList = usedGoodsService.getUsedGoodsListBy(
            request.getUsedGoodsIdList(), UsedGoodsStatus.REGISTERED);

        List<Long> goodsIdList = usedGoodsEntityList.stream()
            .map(usedGoodsEntity -> usedGoodsEntity.getGoodsId()).toList();

        // goodsIdList 로 goods 상태를 STORAGE(보관) 으로 변경
        usedGoodsEntityList.stream().map(usedGoodsEntity -> usedGoodsEntity.getGoodsId())
            .forEach(goodsId -> setGoodsStatusBy(goodsId, GoodsStatus.STORAGE));

        // usedGoodsStatus 를 UNREGISTERED 로 변경
        usedGoodsService.setUsedGoodsStatusBy(usedGoodsEntityList, UsedGoodsStatus.UNREGISTERED);

        return usedGoodsConverter.toMessageResponse("중고 물품 등록이 취소되었습니다.");
    }

    public UsedGoodsDetailResponse getUsedGoodsDetail(Long usedGoodsId) {

        // 등록되어 있는 UsedGoodsEntity
        UsedGoodsEntity usedGoodsEntity = usedGoodsService.getUsedGoodsBy(usedGoodsId,
            UsedGoodsStatus.REGISTERED);

        GoodsEntity goodsEntity = getGoodsBy(usedGoodsEntity.getGoodsId());

        ImageListResponse imageListResponse = imageConverter.toImageListResponse(goodsEntity);

        GoodsResponse goodsResponse = goodsConverter.toResponse(goodsEntity, imageListResponse);

        return usedGoodsConverter.toResponse(usedGoodsEntity, goodsResponse);
    }

    public List<UsedGoodsSearchResponse> usedGoodsSearchBy(SearchCondition condition,
        Pageable page) {

        return this.usedGoodsSearchBy(condition, page, null);

    }

    public List<UsedGoodsSearchResponse> usedGoodsSearchBy(SearchCondition condition, Pageable page,
        String email) {

        Long userId = email != null ? usersService.getUserWithThrow(email).getId() : null;

        EntitySearchCondition entitySearchCondition = usedGoodsConverter.toEntitySearchCondition(
            condition, page, userId);

        List<UsedGoodsEntity> usedGoodsEntityList = usedGoodsService.usedGoodsSearchBy(
            entitySearchCondition);

        Map<Long, GoodsEntity> goodsEntityMap = getGoodsEntityMap(usedGoodsEntityList);

        return usedGoodsEntityList.stream().map(usedGoodsEntity -> {
            Long goodsId = usedGoodsEntity.getGoodsId();
            GoodsEntity goodsEntity = goodsEntityMap.get(goodsId);
            ImageListResponse imageListResponse = imageConverter.toImageListResponse(goodsEntity);
            GoodsResponse response = goodsConverter.toResponse(goodsEntity, imageListResponse);
            return usedGoodsConverter.toSearchResponse(usedGoodsEntity, response);
        }).toList();

    }

    /**
     * --------- 중고 거래 로직 ---------
     */
    public UsedGoodsOrderResponse requestOrder(Long usedGoodsId, String email) {

        // 1. 구매자의 userId
        Long userId = getUserId(email);

        // 2. UsedGoods 조회
        UsedGoodsEntity usedGoodsEntity = usedGoodsService.getUsedGoodsBy(usedGoodsId);

        // 3. 자신이 올린 물품을 구매하려는 경우 방지
        if (usedGoodsEntity.getUserId().equals(userId)) {
            throw new InvalidOrderAuthorityException(UsedGoodsErrorCode.INVALID_ORDER_AUTHORITY);
        }

        // 4. 구매자가 이미 거래 요청을 했는지 확인
        if (usedGoodsService.hasExistingOrder(usedGoodsId, userId)) {
            throw new ExistingOrderException(UsedGoodsErrorCode.EXISTING_ORDER);
        }

        UsedGoodsOrderEntity orderEntity = usedGoodsOrderConverter.toEntity(usedGoodsEntity,
            userId);

        // 5. 새로운 요청서 생성, status = REQUESTED
        UsedGoodsOrderEntity savedOrderEntity = usedGoodsService.requestOrder(orderEntity);

        // 6, UsedGoodsStatus = DEALING (거래중) 으로 변경
        setUsedGoodsStatusBy(usedGoodsEntity, UsedGoodsStatus.DEALING);

        return usedGoodsOrderConverter.toResponse(savedOrderEntity);
    }

    /**
     * 판매자는 구매 요청 내역을 조회할 수 있다.
     */
    public List<UsedGoodsOrderResponse> getOrderList(Long usedGoodsId, String email) {

        // 판매자 userId
        Long userId = getUserId(email);

        // 1. usedGoodsId 로 판매자 ID 조회
        UsedGoodsEntity usedGoodsEntity = usedGoodsService.getUsedGoodsBy(usedGoodsId);

        // 2. 판매자 권한 확인
        validateSellerAuthority(usedGoodsEntity, userId);

        List<UsedGoodsOrderEntity> orderEntityList = usedGoodsService.getUsedGoodsOrderListBy(
            usedGoodsId);

        return usedGoodsOrderConverter.toResponse(orderEntityList);
    }

    public UsedGoodsStatusResponse approveOrder(Long usedGoodsOrderId, String email) {

        Long userId = getUserId(email);

        UsedGoodsOrderEntity orderEntity = getUsedGoodsOrderBy(usedGoodsOrderId);

        // 1. usedGoodsId 로 판매자 ID 조회
        UsedGoodsEntity entity = usedGoodsService.getUsedGoodsBy(orderEntity.getUsedGoodsId());

        // 2. 판매자 권한 확인
        validateSellerAuthority(entity, userId);

        // 3. 요청서 목록 조회
        List<UsedGoodsOrderEntity> orderList = usedGoodsService.getUsedGoodsOrderListBy(
            orderEntity.getUsedGoodsId());

        // 4. 요청서 목록 중 APPROVED 가 있는 경우 예외 -> 2중 승인 방지
        validateApprovedOrder(orderList);

        // 5. UsedGoodsOrderStatus = APPROVED 로 업데이트 (거래 승인)
        usedGoodsService.setUsedGoodsOrderStatusBy(orderEntity, UsedGoodsOrderStatus.APPROVED);

        // 6. 나머지 요청서를 HOLD 로
        orderList.stream()
            .filter(order -> !order.getStatus().equals(UsedGoodsOrderStatus.APPROVED))
            .forEach(order -> usedGoodsService.setUsedGoodsOrderStatusBy(order,
                UsedGoodsOrderStatus.HOLD));

        // 7. UsedGoodsStatus = DEALING 조회 업데이트
        UsedGoodsEntity usedGoodsEntity = usedGoodsService.getUsedGoodsBy(
            orderEntity.getUsedGoodsId(), UsedGoodsStatus.DEALING);

        // 8. UsedGoodsStatus = DEAL 업데이트
        setUsedGoodsStatusBy(usedGoodsEntity, UsedGoodsStatus.DEAL);

        return usedGoodsOrderConverter.toResponse(UsedGoodsStatus.DEAL);
    }

    public UsedGoodsStatusResponse transferOrder(Long usedGoodsOrderId, String email) {

        Long userId = getUserId(email);

        // 1. 거래 요청서 조회
        UsedGoodsOrderEntity orderEntity = getUsedGoodsOrderBy(usedGoodsOrderId);

        // 2. 자신의 요청서인지 검증
        validateOrderAuthority(orderEntity, userId);

        // 3. UsedGoodsStatus = DEAL (중고 거래 확정) 조회
        UsedGoodsEntity usedGoodsEntity = usedGoodsService.getUsedGoodsBy(
            orderEntity.getUsedGoodsId(), UsedGoodsStatus.DEAL);

        // 4. 송금 시 UsedGoodsStatus = REMITTANCE (송금 확인) 으로 변경
        setUsedGoodsStatusBy(usedGoodsEntity, UsedGoodsStatus.REMITTANCE);

        return usedGoodsOrderConverter.toResponse(UsedGoodsStatus.REMITTANCE);
    }

    public UsedGoodsStatusResponse receiveUsedGoods(Long usedGoodsOrderId, String email) {

        Long userId = getUserId(email);

        // 1. 거래 요청서 조회
        UsedGoodsOrderEntity orderEntity = getUsedGoodsOrderBy(usedGoodsOrderId);

        // 2. 자신의 요청서인지 검증
        validateOrderAuthority(orderEntity, userId);

        // 3. UsedGoodsStatus = REMITTANCE (송금 확인) 조회
        UsedGoodsEntity usedGoodsEntity = usedGoodsService.getUsedGoodsBy(
            orderEntity.getUsedGoodsId(), UsedGoodsStatus.REMITTANCE);

        // 4. UsedGoodsStatus = ASSIGNMENT (물건 확인) 으로 변경
        setUsedGoodsStatusBy(usedGoodsEntity, UsedGoodsStatus.ASSIGNMENT);

        return usedGoodsOrderConverter.toResponse(UsedGoodsStatus.ASSIGNMENT);
    }

    public UsedGoodsStatusResponse completeOrder(Long usedGoodsOrderId, String email) {

        Long userId = getUserId(email);

        // 1. 거래 요청서 조회
        UsedGoodsOrderEntity orderEntity = getUsedGoodsOrderBy(usedGoodsOrderId);

        // 2. UsedGoodsStatus = ASSIGNMENT (물건 확인) 조회
        UsedGoodsEntity usedGoodsEntity = usedGoodsService.getUsedGoodsBy(
            orderEntity.getUsedGoodsId(), UsedGoodsStatus.ASSIGNMENT);

        // 3. 권한 확인
        validateSellerAuthority(usedGoodsEntity, userId);

        // 4. UsedGoodsStatus = SOLD (판매) 으로 변경
        setUsedGoodsStatusBy(usedGoodsEntity, UsedGoodsStatus.SOLD);

        return usedGoodsOrderConverter.toResponse(UsedGoodsStatus.SOLD);
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

    private void setGoodsStatusBy(Long goodId, GoodsStatus status) {
        goodsService.setGoodsStatusBy(goodId, status);
    }

    private GoodsEntity getGoodsBy(Long goodsId) {
        return goodsService.getGoodsBy(goodsId);
    }

    private UsedGoodsOrderEntity getUsedGoodsOrderBy(Long usedGoodsOrderId) {
        return usedGoodsService.getUsedGoodsOrderBy(usedGoodsOrderId);
    }

    private Long getUserId(String email) {
        return usersService.getUserWithThrow(email).getId();
    }

    private static void validateApprovedOrder(List<UsedGoodsOrderEntity> orderList) {
        orderList.stream()
            .filter(orderEntity -> orderEntity.getStatus().equals(UsedGoodsOrderStatus.APPROVED))
            .findFirst()
            .ifPresent(approvedOrder -> {
                throw new ApprovedOrderException(UsedGoodsErrorCode.APPROVED_ORDER);
            });
    }

    private static void validateOrderAuthority(UsedGoodsOrderEntity orderEntity, Long userId) {
        if (!orderEntity.getUserId().equals(userId)) {
            throw new InvalidOrderAuthorityException(UsedGoodsErrorCode.INVALID_ORDER_AUTHORITY);
        }
    }

    private static void validateSellerAuthority(UsedGoodsEntity usedGoodsEntity, Long userId) {
        if (!usedGoodsEntity.getUserId().equals(userId)) {
            throw new InvalidOrderAuthorityException(UsedGoodsErrorCode.INVALID_ORDER_AUTHORITY);
        }
    }

    private void setUsedGoodsStatusBy(UsedGoodsEntity usedGoodsEntity, UsedGoodsStatus status) {
        usedGoodsService.setUsedGoodsStatusBy(usedGoodsEntity, status);
    }

}