package image.common.exception;

import global.api.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import image.common.error.UsedGoodsErrorCode;
import image.common.exception.usedGoods.ApprovedOrderException;
import image.common.exception.usedGoods.ExistingOrderException;
import image.common.exception.usedGoods.GoodsNotInUsedStatus;
import image.common.exception.usedGoods.InvalidOrderAuthorityException;
import image.common.exception.usedGoods.UsedGoodsNotFoundException;
import image.common.exception.usedGoods.UsedGoodsOrderNotFoundException;

@Slf4j
@RestControllerAdvice
public class UsedGoodsExceptionHandler {

    @ExceptionHandler(value = UsedGoodsNotFoundException.class)
    public ResponseEntity<Api<Object>> usedGoodsNotFoundException(UsedGoodsNotFoundException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(Api.ERROR(UsedGoodsErrorCode.USED_GOODS_NOT_FOUND));
    }

    @ExceptionHandler(value = GoodsNotInUsedStatus.class)
    public ResponseEntity<Api<Object>> goodsNotInUsedStatus(GoodsNotInUsedStatus e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Api.ERROR(UsedGoodsErrorCode.GOODS_NOT_IN_USED_STATUS));
    }

    @ExceptionHandler(value = ApprovedOrderException.class)
    public ResponseEntity<Api<Object>> approvedOrderException(ApprovedOrderException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Api.ERROR(UsedGoodsErrorCode.APPROVED_ORDER));
    }

    @ExceptionHandler(value = ExistingOrderException.class)
    public ResponseEntity<Api<Object>> existingOrderException(ExistingOrderException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Api.ERROR(UsedGoodsErrorCode.EXISTING_ORDER));
    }

    @ExceptionHandler(value = InvalidOrderAuthorityException.class)
    public ResponseEntity<Api<Object>> invalidOrderAuthorityException(
        InvalidOrderAuthorityException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Api.ERROR(UsedGoodsErrorCode.INVALID_ORDER_AUTHORITY));
    }

    @ExceptionHandler(value = UsedGoodsOrderNotFoundException.class)
    public ResponseEntity<Api<Object>> usedGoodsOrderNotFoundException(
        UsedGoodsOrderNotFoundException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Api.ERROR(UsedGoodsErrorCode.USED_GOODS_ORDER_NOT_FOUND));
    }

}
