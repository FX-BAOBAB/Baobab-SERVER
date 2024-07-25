package db.domain.takeback;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TakeBackRepository extends JpaRepository<TakeBackEntity,Long> {

    Optional<TakeBackEntity> findFirstById(Long requestId);

}

