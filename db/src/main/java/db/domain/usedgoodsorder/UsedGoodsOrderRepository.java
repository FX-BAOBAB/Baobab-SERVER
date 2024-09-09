package db.domain.usedgoodsorder;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsedGoodsOrderRepository extends JpaRepository<UsedGoodsOrderEntity, Long> {

    List<UsedGoodsOrderEntity> findAllByUsedGoodsId(Long usedGoodsId);

    List<UsedGoodsOrderEntity> findAllBySellerId(Long userId);

    List<UsedGoodsOrderEntity> findAllByBuyerId(Long userId);

    Optional<UsedGoodsOrderEntity> findFirstById(Long usedGoodsOrderId);

    Optional<UsedGoodsOrderEntity> findByUsedGoodsIdAndBuyerId(Long usedGoodsId, Long buyerId);

}
