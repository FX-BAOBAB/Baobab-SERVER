package store.common.error;

import global.errorcode.ErrorCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GoodsLedgerErrorCode implements ErrorCodeIfs {

    GOODS_LEDGER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), 2000, "물품 관리 이력이 존재하지 않습니다."),
    ;

    private final Integer httpCode;
    private final Integer errorCode;
    private final String description;

}
