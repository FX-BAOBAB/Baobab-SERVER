package warehouse.domain.usedgoods.business;

import db.domain.usedgoods.UsedGoodsEntity;
import db.domain.usedgoods.enums.UsedGoodsStatus;
import db.domain.usedgoodsorder.UsedGoodsOrderEntity;
import db.domain.usedgoodsorder.enums.UsedGoodsOrderStatus;
import global.annotation.Business;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import warehouse.common.error.UsedGoodsErrorCode;
import warehouse.common.exception.usedGoods.ApprovedOrderException;
import warehouse.common.exception.usedGoods.ExistingOrderException;
import warehouse.common.exception.usedGoods.InvalidOrderAuthorityException;
import warehouse.domain.usedgoods.controller.model.response.UsedGoodsOrderResponse;
import warehouse.domain.usedgoods.controller.model.response.UsedGoodsStatusResponse;
import warehouse.domain.usedgoods.converter.UsedGoodsOrderConverter;
import warehouse.domain.usedgoods.service.UsedGoodsOrderService;
import warehouse.domain.usedgoods.service.UsedGoodsService;
import warehouse.domain.users.service.UsersService;

@Business
@RequiredArgsConstructor
@Slf4j
public class UsedGoodsOrderBusiness {

    private final UsersService usersService;
    private final UsedGoodsOrderService usedGoodsOrderService;
    private final UsedGoodsService usedGoodsService;
    private final UsedGoodsOrderConverter usedGoodsOrderConverter;


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
        if (usedGoodsOrderService.hasExistingOrder(usedGoodsId, userId)) {
            throw new ExistingOrderException(UsedGoodsErrorCode.EXISTING_ORDER);
        }

        UsedGoodsOrderEntity orderEntity = usedGoodsOrderConverter.toEntity(usedGoodsEntity,
            userId);

        // 5. 새로운 요청서 생성, status = REQUESTED
        UsedGoodsOrderEntity savedOrderEntity = usedGoodsOrderService.requestOrder(orderEntity);

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

        List<UsedGoodsOrderEntity> orderEntityList = usedGoodsOrderService.getUsedGoodsOrderListBy(
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
        List<UsedGoodsOrderEntity> orderList = usedGoodsOrderService.getUsedGoodsOrderListBy(
            orderEntity.getUsedGoodsId());

        // 4. 요청서 목록 중 APPROVED 가 있는 경우 예외 -> 2중 승인 방지
        validateApprovedOrder(orderList);

        // 5. UsedGoodsOrderStatus = APPROVED 로 업데이트 (거래 승인)
        usedGoodsOrderService.setUsedGoodsOrderStatusBy(orderEntity, UsedGoodsOrderStatus.APPROVED);

        // 6. 나머지 요청서를 HOLD 로
        orderList.stream()
            .filter(order -> !order.getStatus().equals(UsedGoodsOrderStatus.APPROVED))
            .forEach(order -> usedGoodsOrderService.setUsedGoodsOrderStatusBy(order,
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

    private UsedGoodsOrderEntity getUsedGoodsOrderBy(Long usedGoodsOrderId) {
        return usedGoodsOrderService.getUsedGoodsOrderBy(usedGoodsOrderId);
    }

    private Long getUserId(String email) {
        return usersService.getUserWithThrow(email).getId();
    }

    private void setUsedGoodsStatusBy(UsedGoodsEntity usedGoodsEntity, UsedGoodsStatus status) {
        usedGoodsService.setUsedGoodsStatusBy(usedGoodsEntity, status);
    }

}