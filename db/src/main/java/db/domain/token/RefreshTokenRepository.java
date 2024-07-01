package db.domain.token;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity,Long> {

    void deleteByUserId(Long userId);

    RefreshTokenEntity findFirstByUserIdOrderByUserId(Long userId);
}
