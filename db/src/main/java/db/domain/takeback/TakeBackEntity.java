package db.domain.takeback;

import db.domain.takeback.enums.TakeBackStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "takeback")
@Builder
public class TakeBackEntity {

    @Id
    private Long receivingId;

    @Enumerated
    @Column(length = 50, nullable = false,columnDefinition = "varchar(50)")
    private TakeBackStatus status;

    private LocalDateTime takeBackRequestAt;

}
