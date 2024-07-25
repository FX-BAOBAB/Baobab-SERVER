package db.domain.image;

import db.domain.image.enums.ImageKind;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageEntity,Long> {

    List<ImageEntity> findAllByGoodsIdOrderByIdDesc(Long goodsId);

    List<ImageEntity> findAllByGoodsIdAndKindOrderByIdDesc(Long goodsId, ImageKind kind);

    Optional<ImageEntity> findFirstByIdOrderByIdDesc(Long imageId);

}
