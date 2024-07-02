package db.domain.image;

import db.domain.image.enums.ImageKind;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageEntity,Long> {

    List<ImageEntity> findAllByGoodsIdOrderByIdDesc(Long goodsId);

    List<ImageEntity> findAllByGoodsIdAndKindOrderByIdDesc(Long goodsId, ImageKind kind);

    ImageEntity findFirstByIdOrderByIdDesc(Long id);

}
