package db.domain.goods;

import db.domain.goods.GoodsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsRepository extends JpaRepository<GoodsEntity,Long> {

    GoodsEntity findAllByReceivingIdOrderByIdDesc(Long receivingId);
}
