package db.domain.usedgoods;

import db.domain.usedgoods.enums.UsedGoodsStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsedGoodsRepository extends JpaRepository<UsedGoodsEntity, Long> {

    Optional<UsedGoodsEntity> findFirstByIdAndStatus(Long usedGoodsId, UsedGoodsStatus status);

    List<UsedGoodsEntity> findByUserId(Long userId);

    Optional<UsedGoodsEntity> findFirstById(Long usedGoodsId);
}
