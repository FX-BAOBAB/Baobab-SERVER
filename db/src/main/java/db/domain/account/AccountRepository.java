package db.domain.account;

import db.domain.users.enums.UserStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    Optional<AccountEntity> findByEmailAndPasswordAndUserStatus(String email, String password,
        UserStatus userStatus);

}
