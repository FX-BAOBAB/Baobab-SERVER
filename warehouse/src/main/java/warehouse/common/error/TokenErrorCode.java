package warehouse.common.error;

import global.errorcode.ErrorCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TokenErrorCode implements ErrorCodeIfs {

    NOT_AUTHENTICATION_USER(401,1100,"로그인이 필요합니다."),
    NOT_AUTHORIZATION_USER(404,1101,"허가된 접근이 아닙니다."),
    USER_NOT_FOUNT(404,1102,"존재하지 않는 사용자입니다."),
    UNREGISTERED_MEMBER(403,1103,"탈퇴한 회원입니다."),
    DORMANT_MEMBER(403,1104,"휴면 계정입니다."),
    INVALID_TOKEN(401,1105,"유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(401,1106,"만료된 토큰입니다."),
    TOKEN_EXCEPTION(401,1107,"알 수 없는 토큰 에러입니다."),
    NON_TOKEN_HEADER(401,1108,"인증 헤더 토큰이 없습니다."),
    NO_EXPIRED_TOKEN(400,1109,"만료되지 않은 토큰입니다."),
    EXIST_USER(403,1110,"이미 존재하는 아이디입니다.")
    ;

    private final Integer httpCode;
    private final Integer errorCode;
    private final String description;

}