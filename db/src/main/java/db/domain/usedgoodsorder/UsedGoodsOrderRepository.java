package db.domain.usedgoodsorder;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsedGoodsOrderRepository extends JpaRepository<UsedGoodsOrderEntity, Long> {

    List<UsedGoodsOrderEntity> findAllByUsedGoodsId(Long usedGoodsId);

    Optional<UsedGoodsOrderEntity> findFirstById(Long usedGoodsOrderId);

    Optional<UsedGoodsOrderEntity> findByUsedGoodsIdAndUserId(Long usedGoodsId, Long userId);

}
