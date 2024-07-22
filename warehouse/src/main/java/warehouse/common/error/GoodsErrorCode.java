package warehouse.common.error;

import global.errorcode.ErrorCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GoodsErrorCode implements ErrorCodeIfs {

    GOODS_NOT_FOUND(HttpStatus.NOT_FOUND.value(), 1200, "물품이 존재하지 않습니다."),
    INVALID_GOODS_STRATEGY(HttpStatus.BAD_REQUEST.value(), 1201, "요청서 종류가 잘못되었습니다.")
    ;

    private final Integer httpCode;
    private final Integer errorCode;
    private final String description;

}
