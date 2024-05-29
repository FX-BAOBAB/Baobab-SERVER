package db.domain.receiving;

import db.common.BaseEntity;
import db.domain.receiving.enums.ReceivingStatus;
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

@Entity
@Table(name = "receiving")
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ReceivingEntity extends BaseEntity {

    @Column(nullable = false)
    private LocalDateTime visitDate;

    @Column(nullable = false, length = 200)
    private String visitAddress;

    @Enumerated(EnumType.STRING)
    @Column(length = 50,columnDefinition = "VARCHAR(50)")
    private ReceivingStatus status;

    private LocalDateTime guaranteeAt;

    private LocalDateTime abandonmentAt;

    @Column(nullable = false)
    private Long userId;

}
