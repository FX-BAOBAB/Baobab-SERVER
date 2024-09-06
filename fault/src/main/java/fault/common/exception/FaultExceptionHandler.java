package fault.common.exception;

import fault.common.error.FaultErrorCode;
import fault.common.exception.fault.ExistsFaultRequestException;
import global.api.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class FaultExceptionHandler {

    @ExceptionHandler(value = ExistsFaultRequestException.class)
    public ResponseEntity<Api<Object>> existsFaultRequestException(ExistsFaultRequestException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Api.ERROR(FaultErrorCode.EXISTS_FAULT_REQUEST));
    }

}
