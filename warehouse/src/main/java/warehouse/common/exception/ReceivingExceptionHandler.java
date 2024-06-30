package warehouse.common.exception;

import global.api.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import warehouse.common.error.ReceivingErrorCode;
import warehouse.common.exception.Receiving.ReceivingNotFoundException;

@Slf4j
@RestControllerAdvice
@Order(value = Integer.MIN_VALUE)
public class ReceivingExceptionHandler {

    @ExceptionHandler(value = ReceivingNotFoundException.class)
    public ResponseEntity<Api<Object>> imageException(ReceivingNotFoundException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(Api.ERROR(ReceivingErrorCode.RECEIVING_REQUEST_NOT_FOUND));
    }
}
