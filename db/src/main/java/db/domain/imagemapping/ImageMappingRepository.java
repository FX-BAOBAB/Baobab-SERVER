package db.domain.imagemapping;

import db.domain.image.enums.ImageKind;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageMappingRepository extends JpaRepository<ImageMappingEntity, Long> {

    List<ImageMappingEntity> findAllByGoodsIdOrderByIdDesc(Long goodsId);

    List<ImageMappingEntity> findAllByGoodsIdAndKindOrderByIdDesc(Long goodsId, ImageKind kind);

}
