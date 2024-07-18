package warehouse.common.exception;

import global.api.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import warehouse.common.error.TakeBackErrorCode;
import warehouse.common.exception.takeback.TakeBackNotAllowedException;


@Slf4j
@RestControllerAdvice
@Order(value = Integer.MIN_VALUE)
public class TakeBackExceptionHandler {

    @ExceptionHandler(value = TakeBackNotAllowedException.class)
    public ResponseEntity<Api<Object>> takeBakeException(TakeBackNotAllowedException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Api.ERROR(TakeBackErrorCode.TAKE_BAKE_NOT_ALLOWED));
    }

}
