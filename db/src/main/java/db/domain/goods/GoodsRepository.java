package db.domain.goods;

import db.domain.goods.enums.GoodsStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsRepository extends JpaRepository<GoodsEntity,Long> {

    List<GoodsEntity> findAllByReceivingIdOrderByIdDesc(Long receivingId);

    Optional<GoodsEntity> findFirstById(Long goodsId);

    List<GoodsEntity> findAllByIdIn(List<Long> goodsIdList);

    List<GoodsEntity> findAllByStatusOrderByIdDesc(GoodsStatus status);

    List<GoodsEntity> findAllByTakeBackIdOrderByIdDesc(Long takeBackId);

    List<GoodsEntity> findAllByShippingIdOrderByIdDesc(Long takeBackId);

}
