package delivery.common.exception;

import delivery.common.error.GoodsErrorCode;
import delivery.common.exception.goods.GoodsNotFoundException;
import delivery.common.exception.goods.GoodsNotInReceivingException;
import delivery.common.exception.goods.GoodsNotInShippingIngException;
import delivery.common.exception.goods.GoodsNotInStorageException;
import delivery.common.exception.goods.InvalidGoodsStatusException;
import delivery.common.exception.receiving.NotOwnerException;
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

    @ExceptionHandler(value = GoodsNotInStorageException.class)
    public ResponseEntity<Api<Object>> goodsNotInStorageException(GoodsNotInStorageException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Api.ERROR(GoodsErrorCode.GOODS_NOT_IN_STORAGE));
    }

    @ExceptionHandler(value = GoodsNotInShippingIngException.class)
    public ResponseEntity<Api<Object>> goodsNotInShippingIngException(GoodsNotInShippingIngException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Api.ERROR(GoodsErrorCode.GOODS_NOT_IN_SHIPPING_ING));
    }

    @ExceptionHandler(value = GoodsNotInReceivingException.class)
    public ResponseEntity<Api<Object>> goodsNotInReceivingException(GoodsNotInReceivingException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Api.ERROR(GoodsErrorCode.GOODS_NOT_IN_RECEIVING));
    }
}
