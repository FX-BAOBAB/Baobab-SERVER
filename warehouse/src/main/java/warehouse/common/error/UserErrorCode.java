package warehouse.common.error;

import global.errorcode.ErrorCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements ErrorCodeIfs {

    FAILED_TO_REGISTER(500,1150,"회원가입이 실패했습니다."),
    USER_NOT_FOUND(404,1151,"사용자를 찾을 수 없습니다."),
    UNREGISTERED_MEMBER(403,1152,"탈퇴한 회원입니다."),
    DORMANT_MEMBER(403,1153,"휴면 계정입니다."),
    EXIST_USER(403,1154,"이미 존재하는 아이디입니다."),
    LOGIN_FAIL(401, 1155, "로그인 정보가 일치하지 않습니다.")
    ;

    private final Integer httpCode;
    private final Integer errorCode;
    private final String description;

}
