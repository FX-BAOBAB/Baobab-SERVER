package db.domain.usedgoods;

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
@Table(name = "usedgoods")
@SuperBuilder
public class UsedGoodsEntity extends BaseEntity {

    @Column(nullable = false, length = 50,columnDefinition = "VARCHAR(50)")
    private String title;

    private int price;

    @Column(nullable = false, length = 1000)
    private String description;

    private LocalDateTime postedAt;

    @Column(nullable = false)
    private Long userId;

}

