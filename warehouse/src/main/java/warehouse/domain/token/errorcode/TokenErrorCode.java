package warehouse.domain.token.errorcode;

import global.errorcode.ErrorCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TokenErrorCode implements ErrorCodeIfs {

    INVALID_TOKEN(401, 1105, "유효하지 않은 토큰입니다."),
    EXPIRE_TOKEN(401, 1106, "Token 이 만료되었습니다."),
    UNKNOWN_TOKEN_EXCEPTION(401, 1107, "알 수 없는 토큰입니다."),
    AUTHORIZATION_TOKEN_NOT_FOUND(401, 1108, "인증 헤더 토큰이 없습니다."),
    VALID_TOKEN(400, 1109, "만료되지 않은 토큰입니다.")
    ;


    private final Integer httpCode;
    private final Integer errorCode;
    private final String description;

}
