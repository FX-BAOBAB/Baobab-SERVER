package db.domain.usedgoodsorder;

import db.common.BaseEntity;
import db.domain.usedgoodsorder.enums.UsedGoodsOrderStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    private Long userId; // 구매자 ID

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50,columnDefinition = "VARCHAR(50)")
    private UsedGoodsOrderStatus status;

    private LocalDateTime createdAt;

    @Column(nullable = false)
    private Long usedGoodsId;

}
