package db.domain.shipping;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingRepository extends JpaRepository<ShippingEntity, Long> {

    List<ShippingEntity> findAllByUserIdOrderByIdDesc(Long userId);

    Optional<ShippingEntity> findFirstById(Long shippingId);

}
