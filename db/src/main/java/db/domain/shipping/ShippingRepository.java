package db.domain.shipping;

import db.domain.shipping.enums.ShippingStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingRepository extends JpaRepository<ShippingEntity, Long> {

    List<ShippingEntity> findAllByUserIdOrderByIdDesc(Long userId);

    Optional<ShippingEntity> findFirstById(Long shippingId);

    List<ShippingEntity> findAllByStatusOrderByDeliveryDate(ShippingStatus shippingStatus);

    List<ShippingEntity> findAllByStatusAndDeliveryDateBetweenOrderByUserId(ShippingStatus status,
        LocalDateTime startDate, LocalDateTime dueDate);

    List<ShippingEntity> findAllByDeliveryManAndStatusAndDeliveryDateBetweenOrderByDeliveryDateDesc(Long userId,ShippingStatus shippingStatus, LocalDateTime startDate, LocalDateTime dueDate);
}
