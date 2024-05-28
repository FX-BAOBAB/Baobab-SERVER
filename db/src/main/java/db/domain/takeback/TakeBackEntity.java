package db.domain.takeback;

import db.common.BaseEntity;
import db.domain.takeback.enums.TakeBackStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
@Table(name = "takeback")
@SuperBuilder
public class TakeBackEntity extends BaseEntity {

    private TakeBackStatus status;
    private Long receivingId;

}
