package warehouse.common.exception;

import global.api.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import warehouse.common.error.GoodsErrorCode;
import warehouse.common.exception.goods.InvalidGoodsStatusException;
import warehouse.common.exception.goods.GoodsNotFoundException;

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
    public ResponseEntity<Api<Object>> InvalidGoodsStatus(
        warehouse.common.exception.goods.InvalidGoodsStatusException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Api.ERROR(GoodsErrorCode.INVALID_GODOS_STATUS));
    }}
