package delivery.common.exception;

import delivery.common.error.ImageErrorCode;
import delivery.common.exception.image.ImageNotFoundException;
import delivery.common.exception.image.ImageStorageException;
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
public class ImageExceptionHandler {

    @ExceptionHandler(value = ImageStorageException.class)
    public ResponseEntity<Api<Object>> imageException(ImageStorageException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Api.ERROR(ImageErrorCode.IMAGE_STORAGE_ERROR));
    }

    @ExceptionHandler(value = ImageNotFoundException.class)
    public ResponseEntity<Api<Object>> imageException(ImageNotFoundException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(Api.ERROR(ImageErrorCode.IMAGE_NOT_FOUND));
    }

}
