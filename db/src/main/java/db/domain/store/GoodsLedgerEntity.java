package db.domain.store;

import db.domain.store.enums.StoreLocation;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "goods_ledger")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsLedgerEntity {

    @Id
    private Long goodsId;

    @Enumerated(EnumType.STRING)
    @Column(length = 50,columnDefinition = "VARCHAR(50)")
    private StoreLocation location;

    @Column(nullable = false)
    private LocalDateTime storedDate;

}
