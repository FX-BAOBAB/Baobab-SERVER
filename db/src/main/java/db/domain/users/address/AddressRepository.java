package db.domain.users.address;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<AddressEntity,Long> {

    List<AddressEntity> findAllByUserIdOrderByIdDesc(Long userId);

}
