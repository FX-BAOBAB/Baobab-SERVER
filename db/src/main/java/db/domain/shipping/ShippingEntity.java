package db.domain.shipping;

import db.common.BaseEntity;
import db.domain.shipping.enums.ShippingStatus;
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
@Table(name = "shipping")
@SuperBuilder
public class ShippingEntity extends BaseEntity {

    private LocalDateTime deliveryDate;

    @Column(nullable = false, length = 200)
    private String deliveryAddress;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false, columnDefinition = "VARCHAR(50)")
    private ShippingStatus status;

    @Column(nullable = false)
    private Long userId;

    private Long deliveryMan;

}
