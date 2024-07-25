package warehouse.common.exception;

import global.api.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import warehouse.common.error.UsedGoodsErrorCode;
import warehouse.common.exception.usedGoods.GoodsNotInUsedStatus;
import warehouse.common.exception.usedGoods.UsedGoodsNotFoundException;

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

}
