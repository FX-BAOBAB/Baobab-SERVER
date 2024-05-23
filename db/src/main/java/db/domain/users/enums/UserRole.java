package db.domain.users.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {

    MASTER("책임자"),
    MANAGER("관리자"),
    BASIC("사용자")
    ;

    private final String description;
}
