package delivery.common.exception;

import delivery.common.error.ReceivingErrorCode;
import delivery.common.exception.receiving.NoOwnershipException;
import delivery.common.exception.receiving.ReceivingNotFoundException;
import delivery.common.exception.receiving.ReceivingNotInCheckingException;
import delivery.common.exception.receiving.ReceivingNotInConfirmationException;
import delivery.common.exception.receiving.ReceivingNotInDeliveryException;
import delivery.common.exception.receiving.ReceivingNotInRegisteredException;
import delivery.common.exception.receiving.ReceivingNotInTakingException;
import global.api.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

    @ExceptionHandler(value = ReceivingNotInTakingException.class)
    public ResponseEntity<Api<Object>> receivingNotInTakingException(ReceivingNotInTakingException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Api.ERROR(ReceivingErrorCode.RECEIVING_NOT_IN_TAKING));
    }

    @ExceptionHandler(value = ReceivingNotInConfirmationException.class)
    public ResponseEntity<Api<Object>> receivingNotInConfirmationException(ReceivingNotInConfirmationException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Api.ERROR(ReceivingErrorCode.RECEIVING_NOT_IN_CONFIRMATION));
    }

    @ExceptionHandler(value = ReceivingNotInDeliveryException.class)
    public ResponseEntity<Api<Object>> receivingNotInDeliveryException(ReceivingNotInDeliveryException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Api.ERROR(ReceivingErrorCode.RECEIVING_NOT_IN_DELIVERY));
    }

    @ExceptionHandler(value = ReceivingNotInRegisteredException.class)
    public ResponseEntity<Api<Object>> receivingNotInRegisteredException(ReceivingNotInRegisteredException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Api.ERROR(ReceivingErrorCode.RECEIVING_NOT_IN_REGISTERED));
    }

    @ExceptionHandler(value = ReceivingNotInCheckingException.class)
    public ResponseEntity<Api<Object>> receivingNotInCheckingException(ReceivingNotInCheckingException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Api.ERROR(ReceivingErrorCode.RECEIVING_NOT_IN_CHECKING));
    }

}
