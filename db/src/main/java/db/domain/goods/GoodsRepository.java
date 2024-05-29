package db.domain.goods;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsRepository extends JpaRepository<GoodsEntity,Long> {

    List<GoodsEntity> findAllByReceivingIdOrderByIdDesc(Long receivingId);
}
