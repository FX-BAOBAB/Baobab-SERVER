package db.domain.image;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageEntity,Long> {

    List<ImageEntity> findAllByGoodsIdOrderByIdDesc(Long goodsId);
}
