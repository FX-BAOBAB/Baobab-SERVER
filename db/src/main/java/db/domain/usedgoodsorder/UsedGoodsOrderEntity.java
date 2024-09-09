package db.domain.usedgoodsorder;

import db.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usedgoods_order")
@SuperBuilder
public class UsedGoodsOrderEntity extends BaseEntity {


    @Column(nullable = false)
    private Long buyerId; // 구매자 ID

    @Column(nullable = false)
    private Long sellerId; // 판매자 ID

    private LocalDateTime createdAt;

    @Column(nullable = false)
    private Long usedGoodsId;

}
