package warehouse.domain.users.errorcode;

import global.errorcode.ErrorCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum UserErrorCode implements ErrorCodeIfs {

    NOT_FOUND_USER(404, 1103, "존재하지 않는 사용자입니다."),
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT.value(), 1104, "이미 존재하는 사용자입니다."),
    INVALID_USER_DATA(HttpStatus.BAD_REQUEST.value(), 1105, "잘못된 사용자 데이터입니다.");

    private final Integer httpCode;
    private final Integer errorCode;
    private final String description;

}
