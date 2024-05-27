package db.domain.users;

import db.domain.users.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT u.status FROM UserEntity u WHERE u.id = :userId")
    UserStatus findUserStatusById(Long userId);

}
