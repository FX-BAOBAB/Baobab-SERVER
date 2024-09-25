package store.common.exception;

import global.api.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import store.common.error.GoodsErrorCode;
import store.common.error.GoodsLedgerErrorCode;
import store.common.exception.goods.GoodsNotFoundException;
import store.common.exception.goods.InvalidGoodsStatusException;
import store.common.exception.goodsledger.GoodsLedgerNotFoundException;
import store.common.exception.receiving.NotOwnerException;

@Slf4j
@RestControllerAdvice
@Order(value = Integer.MIN_VALUE)
public class GoodsLedgerExceptionHandler {

    @ExceptionHandler(value = GoodsLedgerNotFoundException.class)
    public ResponseEntity<Api<Object>> goodsLedgerNotFoundException(GoodsLedgerNotFoundException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(Api.ERROR(GoodsLedgerErrorCode.GOODS_LEDGER_NOT_FOUND));
    }
}
