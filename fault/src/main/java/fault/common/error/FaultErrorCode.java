package fault.common.error;

import global.errorcode.ErrorCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FaultErrorCode implements ErrorCodeIfs {


    ;

    private final Integer httpCode;
    private final Integer errorCode;
    private final String description;

}