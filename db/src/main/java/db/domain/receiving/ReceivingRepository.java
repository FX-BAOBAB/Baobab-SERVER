package db.domain.receiving;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceivingRepository extends JpaRepository<ReceivingEntity, Long> {

    Optional<ReceivingEntity> findFirstById(Long receivingId);

}
