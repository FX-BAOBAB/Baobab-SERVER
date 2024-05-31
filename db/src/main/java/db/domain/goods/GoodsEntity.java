package db.domain.goods;

import db.common.BaseEntity;
import db.domain.goods.enums.GoodsCategory;
import db.domain.receiving.ReceivingEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "goods")
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class GoodsEntity extends BaseEntity {

    @Column(length = 100, nullable = false)
    private String name;

    @Column(nullable = false, length = 50)
    private String modelName;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false, columnDefinition = "VARCHAR(50)")
    private GoodsCategory category;

    private int quantity;

    private LocalDateTime abandonmentAt;

    @Column(nullable = false)
    private Long receivingId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long takeBackId;

}
