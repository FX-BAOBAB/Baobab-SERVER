package warehouse.common.exception;

import global.api.Api;
import global.errorcode.ErrorCode;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(value = ValidationException.class)
    public ResponseEntity<Api<Object>> validateException(ValidationException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Api.ERROR(ErrorCode.INVALID_INPUT_DATA,e.getMessage()));
    }
}
