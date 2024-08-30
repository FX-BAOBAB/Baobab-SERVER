package image.common.exception;

import global.api.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import image.common.error.ReceivingErrorCode;
import image.common.exception.receiving.NoOwnershipException;
import image.common.exception.receiving.ReceivingNotFoundException;

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

    @ExceptionHandler(value = NoOwnershipException.class)
    public ResponseEntity<Api<Object>> noOwnershipException(NoOwnershipException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Api.ERROR(ReceivingErrorCode.NO_OWNERSHIP));
    }
}
