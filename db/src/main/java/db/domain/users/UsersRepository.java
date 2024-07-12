package db.domain.users;

import db.domain.users.enums.UserStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<UserEntity,Long> {

    Optional<UserEntity> findFirstByEmailAndStatusOrderByIdDesc(String email, UserStatus status);

    Optional<UserEntity> findFirstByEmailAndStatusNotOrderByEmailDesc(String email,UserStatus status);

    Optional<UserEntity> findFirstByIdAndStatusOrderByIdDesc(Long userId, UserStatus userStatus);

    boolean existsByEmail(String email);

    boolean existsByName(String name);

}
