package warehouse.domain.usedgoods.service;

import db.domain.usedgoods.EntitySearchCondition;
import db.domain.usedgoods.QueryUsedGoodsRepository;
import db.domain.usedgoods.UsedGoodsEntity;
import db.domain.usedgoods.UsedGoodsRepository;
import db.domain.usedgoods.enums.UsedGoodsStatus;
import db.domain.usedgoodsorder.UsedGoodsOrderEntity;
import db.domain.usedgoodsorder.UsedGoodsOrderRepository;
import db.domain.usedgoodsorder.enums.UsedGoodsOrderStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import warehouse.common.error.UsedGoodsErrorCode;
import warehouse.common.exception.usedGoods.GoodsNotInUsedStatus;
import warehouse.common.exception.usedGoods.UsedGoodsNotFoundException;
import warehouse.common.exception.usedGoods.UsedGoodsOrderNotFoundException;

@Service
@RequiredArgsConstructor
public class UsedGoodsService {

    private final UsedGoodsRepository usedGoodsRepository;
    private final QueryUsedGoodsRepository queryUsedGoodsRepository;
    private final UsedGoodsOrderRepository usedGoodsOrderRepository;

    public void register(UsedGoodsEntity usedGoodsEntity) {
        usedGoodsEntity.setPostedAt(LocalDateTime.now());
        usedGoodsEntity.setStatus(UsedGoodsStatus.REGISTERED);
        usedGoodsRepository.save(usedGoodsEntity);
    }

    public UsedGoodsEntity getUsedGoodsBy(Long usedGoodsId, UsedGoodsStatus status) {
        return usedGoodsRepository.findFirstByIdAndStatus(usedGoodsId, status).orElseThrow(
            () -> new GoodsNotInUsedStatus(UsedGoodsErrorCode.GOODS_NOT_IN_USED_STATUS));
    }

    public UsedGoodsEntity getUsedGoodsBy(Long usedGoodsId) {
        return usedGoodsRepository.findFirstById(usedGoodsId).orElseThrow(
            () -> new UsedGoodsNotFoundException(UsedGoodsErrorCode.USED_GOODS_NOT_FOUND));
    }

    public List<UsedGoodsEntity> getUsedGoodsListBy(List<Long> usedGoodsIdList,
        UsedGoodsStatus status) {
        return usedGoodsIdList.stream()
            .map(usedGoodsId -> this.getUsedGoodsBy(usedGoodsId, status))
            .collect(Collectors.toList());
    }

    public void setUsedGoodsStatusBy(UsedGoodsEntity usedGoodsEntity, UsedGoodsStatus status) {
        usedGoodsEntity.setStatus(status);
        usedGoodsRepository.save(usedGoodsEntity);
    }

    public void setUsedGoodsStatusBy(List<UsedGoodsEntity> usedGoodsEntityList,
        UsedGoodsStatus status) {
        usedGoodsEntityList.forEach(usedGoodsEntity -> {
            this.setUsedGoodsStatusBy(usedGoodsEntity, status);
        });
    }

    public List<UsedGoodsEntity> usedGoodsSearchBy(EntitySearchCondition condition) {
        List<UsedGoodsEntity> searchList = queryUsedGoodsRepository.usedGoodsSearchBy(condition);
        if (searchList.isEmpty()) {
            throw new UsedGoodsNotFoundException(UsedGoodsErrorCode.USED_GOODS_NOT_FOUND);
        }
        return searchList;
    }

    public UsedGoodsOrderEntity requestOrder(UsedGoodsOrderEntity orderEntity) {
        orderEntity.setStatus(UsedGoodsOrderStatus.REGISTERED);
        orderEntity.setCreatedAt(LocalDateTime.now());
        return usedGoodsOrderRepository.save(orderEntity);
    }

    public List<UsedGoodsOrderEntity> getUsedGoodsOrderListBy(Long usedGoodsId) {
        List<UsedGoodsOrderEntity> orderEntityList = usedGoodsOrderRepository.findAllByUsedGoodsId(
            usedGoodsId);
        if (orderEntityList.isEmpty()) {
            throw new UsedGoodsOrderNotFoundException(
                UsedGoodsErrorCode.USED_GOODS_ORDER_NOT_FOUND);
        }
        return orderEntityList;
    }

    public UsedGoodsOrderEntity getUsedGoodsOrderBy(Long usedGoodsOrderId) {
        return usedGoodsOrderRepository.findFirstById(usedGoodsOrderId)
            .orElseThrow(() -> new UsedGoodsOrderNotFoundException(
                UsedGoodsErrorCode.USED_GOODS_ORDER_NOT_FOUND));
    }

    public void setUsedGoodsOrderStatusBy(UsedGoodsOrderEntity orderEntity,
        UsedGoodsOrderStatus status) {
        orderEntity.setStatus(status);
        usedGoodsOrderRepository.save(orderEntity);
    }

    public Boolean hasExistingOrder(Long usedGoodsId, Long userId) {
        return usedGoodsOrderRepository.findByUsedGoodsIdAndUserId(usedGoodsId, userId).isPresent();
    }

}
