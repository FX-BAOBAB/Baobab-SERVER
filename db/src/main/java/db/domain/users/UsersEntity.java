package db.domain.users;

import db.common.BaseEntity;
import db.domain.users.enums.UserRole;
import db.domain.users.enums.UserStatus;
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
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Table(name = "users")
public class UsersEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50,columnDefinition = "VARCHAR(50)")
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50,columnDefinition = "VARCHAR(50)")
    private UserStatus status;

    @Column(nullable = false, length = 100)
    private String email;
    
    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 100)
    private String name;

    private LocalDateTime registeredAt;

    private LocalDateTime unRegisteredAt;

    private LocalDateTime lastLoginAt;

}
