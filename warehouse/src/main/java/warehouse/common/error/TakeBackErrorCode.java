package warehouse.common.error;

import global.errorcode.ErrorCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TakeBackErrorCode implements ErrorCodeIfs {

    TAKE_BAKE_NOT_ALLOWED(HttpStatus.BAD_REQUEST.value(), 1400, "반품을 진행할 수 없습니다."),
    NOT_FOUNT_REQUEST(HttpStatus.BAD_REQUEST.value(), 1401, "반품 요청서를 찾을 수 없습니다.")
    ;

    private final Integer httpCode;
    private final Integer errorCode;
    private final String description;

}
