package db.domain.goods.enums;

import db.domain.goods.GoodsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsRepository extends JpaRepository<GoodsEntity,Long> {

}
