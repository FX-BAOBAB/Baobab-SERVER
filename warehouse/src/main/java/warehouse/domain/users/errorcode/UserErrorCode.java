package warehouse.domain.users.errorcode;

import global.errorcode.ErrorCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserErrorCode implements ErrorCodeIfs {

    NOT_FOUND_USER(404, 1103, "존재하지 않는 사용자입니다.");

    private final Integer httpCode;
    private final Integer errorCode;
    private final String description;

}
