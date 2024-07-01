package db.domain.users.address;

import db.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Table(name = "address")
public class AddressEntity extends BaseEntity {

    @Column(nullable = false, length = 200)
    private String address;

    @Column(nullable = false, length = 200)
    private String detailAddress;

    private int post;

    private boolean basicAddress;

    private Long userId;

}
