package warehouse.domain.usedgoods.service;

import db.domain.usedgoodsorder.UsedGoodsOrderEntity;
import db.domain.usedgoodsorder.UsedGoodsOrderRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import warehouse.common.error.UsedGoodsErrorCode;
import warehouse.common.exception.usedGoods.UsedGoodsOrderNotFoundException;

@Service
@RequiredArgsConstructor
public class UsedGoodsOrderService {

    private final UsedGoodsOrderRepository usedGoodsOrderRepository;

    public UsedGoodsOrderEntity orderUsedGoods(UsedGoodsOrderEntity orderEntity) {
        return usedGoodsOrderRepository.save(orderEntity);
    }

    public List<UsedGoodsOrderEntity> getUsedGoodsOrderListByBuyerId(Long userId) {
        List<UsedGoodsOrderEntity> orderEntityList = usedGoodsOrderRepository.findAllByBuyerId(
            userId);
        return getValidatedOrderList(orderEntityList);
    }

    public List<UsedGoodsOrderEntity> getUsedGoodsOrderListBySellerId(Long userId) {
        List<UsedGoodsOrderEntity> orderEntityList = usedGoodsOrderRepository.findAllBySellerId(
            userId);
        return getValidatedOrderList(orderEntityList);
    }

    private List<UsedGoodsOrderEntity> getValidatedOrderList(
        List<UsedGoodsOrderEntity> orderEntityList) {
        if (orderEntityList.isEmpty()) {
            throw new UsedGoodsOrderNotFoundException(
                UsedGoodsErrorCode.USED_GOODS_ORDER_NOT_FOUND);
        }
        return orderEntityList;
    }

    public Boolean hasExistingOrder(Long usedGoodsId, Long buyerId) {
        return usedGoodsOrderRepository.findByUsedGoodsIdAndBuyerId(usedGoodsId, buyerId)
            .isPresent();
    }

    public UsedGoodsOrderEntity getUsedGoodsOrderBy(Long usedGoodsId) {
        return usedGoodsOrderRepository.findFirstByUsedGoodsId(usedGoodsId).orElseThrow(
            () -> new UsedGoodsOrderNotFoundException(
                UsedGoodsErrorCode.USED_GOODS_ORDER_NOT_FOUND));
    }
}
