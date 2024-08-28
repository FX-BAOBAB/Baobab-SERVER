package image.common.exception;

import global.api.Api;
import image.common.exception.Goods.GoodsNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import image.common.error.GoodsErrorCode;
import image.common.exception.Goods.InvalidGoodsStatusException;
import image.common.exception.receiving.NotOwnerException;

@Slf4j
@RestControllerAdvice
@Order(value = Integer.MIN_VALUE)
public class GoodsExceptionHandler {

    @ExceptionHandler(value = GoodsNotFoundException.class)
    public ResponseEntity<Api<Object>> imageException(GoodsNotFoundException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(Api.ERROR(GoodsErrorCode.GOODS_NOT_FOUND));
    }

    @ExceptionHandler(value = InvalidGoodsStatusException.class)
    public ResponseEntity<Api<Object>> InvalidGoodsStatus(InvalidGoodsStatusException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Api.ERROR(GoodsErrorCode.INVALID_GOODS_STATUS));
    }

    @ExceptionHandler(value = NotOwnerException.class)
    public ResponseEntity<Api<Object>> notOwnerException(NotOwnerException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Api.ERROR(GoodsErrorCode.NOT_OWNER));
    }
}
