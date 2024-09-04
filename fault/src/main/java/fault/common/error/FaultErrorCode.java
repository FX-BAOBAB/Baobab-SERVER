package fault.common.error;

import global.errorcode.ErrorCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum FaultErrorCode implements ErrorCodeIfs {

    EXISTS_FAULT_REQUEST(HttpStatus.BAD_REQUEST.value(),1700,"등록된 결함 요총서가 존재합니다.")
    ;

    private final Integer httpCode;
    private final Integer errorCode;
    private final String description;

}