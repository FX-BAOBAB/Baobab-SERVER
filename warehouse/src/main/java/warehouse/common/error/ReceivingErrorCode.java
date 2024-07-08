package warehouse.common.error;

import global.errorcode.ErrorCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ReceivingErrorCode implements ErrorCodeIfs {

    RECEIVING_REQUEST_NOT_FOUND(HttpStatus.NOT_FOUND.value(), 1250, "입고 요청서가 존재하지 않습니다."),
    NO_OWNERSHIP(HttpStatus.BAD_REQUEST.value(), 1251, "사용자의 물품이 아닙니다.")
    ;

    private final Integer httpCode;
    private final Integer errorCode;
    private final String description;
}