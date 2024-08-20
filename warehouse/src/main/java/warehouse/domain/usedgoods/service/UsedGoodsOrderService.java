package warehouse.domain.usedgoods.service;

import db.domain.usedgoodsorder.UsedGoodsOrderEntity;
import db.domain.usedgoodsorder.UsedGoodsOrderRepository;
import db.domain.usedgoodsorder.enums.UsedGoodsOrderStatus;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import warehouse.common.error.UsedGoodsErrorCode;
import warehouse.common.exception.usedGoods.UsedGoodsOrderNotFoundException;

@Service
@RequiredArgsConstructor
public class UsedGoodsOrderService {

    private final UsedGoodsOrderRepository usedGoodsOrderRepository;

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
