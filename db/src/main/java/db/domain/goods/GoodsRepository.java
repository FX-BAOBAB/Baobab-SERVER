package db.domain.goods;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsRepository extends JpaRepository<GoodsEntity,Long> {

    List<GoodsEntity> findAllByReceivingIdOrderByIdDesc(Long receivingId);

    Optional<GoodsEntity> findFirstById(Long goodsId);

    List<GoodsEntity> findAllByIdIn(List<Long> goodsIdList);

    List<GoodsEntity> findAllByReceivingId(Long receivingId);

}
