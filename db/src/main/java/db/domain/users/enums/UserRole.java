package db.domain.users.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {

    MASTER("마스터"),
    MANAGER("관리자"),
    BASIC("일반사용자");

    private final String description;

}
