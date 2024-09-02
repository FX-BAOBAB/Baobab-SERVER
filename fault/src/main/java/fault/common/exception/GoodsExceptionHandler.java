package fault.common.exception;

import fault.common.exception.goods.GoodsNotFoundException;
import fault.common.exception.goods.InvalidGoodsStatusException;
import fault.common.error.GoodsErrorCode;
import fault.common.exception.receiving.NotOwnerException;
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
