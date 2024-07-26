package db.domain.takeback;

import db.common.BaseEntity;
import db.domain.takeback.enums.TakeBackStatus;
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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "takeback")
@SuperBuilder
public class TakeBackEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false,columnDefinition = "varchar(50)")
    private TakeBackStatus status;

    private LocalDateTime takeBackRequestAt;

    @Column(nullable = false)
    private Long userId;

}
