package db.domain.users.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {

    MASTER("마스터"),
    STORE_MANAGER("관리자"),
    BASIC_USER("일반사용자"),
    DELIVERY_MAN("배송자")
    ;

    private final String description;

}
