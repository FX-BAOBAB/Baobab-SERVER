package db.domain.receiving;

import db.domain.receiving.enums.ReceivingStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import jdk.jshell.Snippet.Kind;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceivingRepository extends JpaRepository<ReceivingEntity, Long> {

    Optional<ReceivingEntity> findFirstById(Long receivingId);

    List<ReceivingEntity> findAllByUserIdOrderByIdDesc(Long userId);

    List<ReceivingEntity> findAllByStatusOrderByVisitDate(ReceivingStatus receivingStatus);

    List<ReceivingEntity> findAllByStatusAndVisitDateBetweenOrderByUserId (ReceivingStatus receivingStatus, LocalDateTime startDate, LocalDateTime dueDate);
}
