package db.domain.goods;

import db.domain.goods.enums.Category;
import db.domain.receiving.ReceivingEntity;
import global.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.CustomLog;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.Length;

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

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false, columnDefinition = "VARCHAR(50)")
    private Category category;

    private int quantity;

    @ManyToOne
    private ReceivingEntity receivingEntity;

}
