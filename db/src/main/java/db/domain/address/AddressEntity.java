package db.domain.address;

import db.common.BaseEntity;
import db.domain.account.AccountEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "address")
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class AddressEntity extends BaseEntity {

    private String address;

    private String detailAddress;

    private boolean basicAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    private AccountEntity account;
}
