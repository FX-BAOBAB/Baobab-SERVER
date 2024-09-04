package db.domain.fault;

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

@Entity
@Table(name = "fault")
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class FaultEntity extends BaseEntity {

    @Column(length = 200, nullable = false)
    private String description;

    @Column(nullable = false)
    private Boolean approval;

    private LocalDateTime registeredAt;

    @Column(nullable = false)
    private Long receivingId;

    @Column(nullable = false)
    private Long userId;

}
