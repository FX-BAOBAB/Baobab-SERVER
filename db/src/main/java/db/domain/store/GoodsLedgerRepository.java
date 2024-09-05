package db.domain.store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsLedgerRepository extends JpaRepository<GoodsLedgerEntity,Long> {

}
