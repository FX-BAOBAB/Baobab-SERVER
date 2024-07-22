package db.domain.goods;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsRepository extends JpaRepository<GoodsEntity,Long> {

    List<GoodsEntity> findAllByReceivingIdOrderByIdDesc(Long receivingId);

    Optional<GoodsEntity> findFirstById(Long goodsId);

    List<GoodsEntity> findAllByIdIn(List<Long> goodsIdList);

    List<GoodsEntity> findAllByTakeBackIdOrderByIdDesc(Long takeBackId);

    //TODO 출고 시스템 완료 후 구현 예정
    //List<GoodsEntity> findAllByShippingIdOrderByIdDesc(Long takeBackId);
}
