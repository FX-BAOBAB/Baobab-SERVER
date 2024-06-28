package db.domain.users;

import db.domain.users.enums.UserStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<UsersEntity,Long> {

    Optional<UsersEntity> findFirstByEmailAndStatusOrderByIdDesc(String email, UserStatus status);

}
