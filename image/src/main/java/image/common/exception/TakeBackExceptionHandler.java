package image.common.exception;

import global.api.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import image.common.error.TakeBackErrorCode;
import image.common.exception.takeback.NotFoundRequestException;
import image.common.exception.takeback.TakeBackNotAllowedException;


@Slf4j
@RestControllerAdvice
public class TakeBackExceptionHandler {

    @ExceptionHandler(value = TakeBackNotAllowedException.class)
    public ResponseEntity<Api<Object>> takeBakeException(TakeBackNotAllowedException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Api.ERROR(TakeBackErrorCode.TAKE_BAKE_NOT_ALLOWED));
    }

    @ExceptionHandler(value = NotFoundRequestException.class)
    public ResponseEntity<Api<Object>> notFoundRequestException(NotFoundRequestException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Api.ERROR(TakeBackErrorCode.NOT_FOUNT_REQUEST));
    }

}
