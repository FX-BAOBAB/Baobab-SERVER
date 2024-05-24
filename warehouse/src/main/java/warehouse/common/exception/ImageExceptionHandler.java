package warehouse.common.exception;

import global.api.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import warehouse.common.error.ImageErrorCode;
import warehouse.common.exception.image.ImageStorageException;

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

}
