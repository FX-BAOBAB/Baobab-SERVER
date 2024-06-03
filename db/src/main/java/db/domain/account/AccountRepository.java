package db.domain.account;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    Optional<AccountEntity> findByEmailAndPassword(String email, String password);
    boolean existsByEmail(String email);
    Optional<AccountEntity> findByEmail(String email);

}
