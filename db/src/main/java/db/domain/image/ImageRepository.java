package db.domain.image;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageEntity,Long> {

    List<ImageEntity> findAllByImageMappingIdInOrderByIdDesc(List<Long> imageMappingIdList);

    Optional<ImageEntity> findFirstByIdOrderByIdDesc(Long imageId);

}
