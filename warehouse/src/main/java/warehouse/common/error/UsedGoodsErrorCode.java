package warehouse.common.error;

import global.errorcode.ErrorCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UsedGoodsErrorCode implements ErrorCodeIfs {

    USED_GOODS_NOT_FOUND(HttpStatus.NOT_FOUND.value(), 1600, "중고 물품을 찾을 수 없습니다.")
    ;

    private final Integer httpCode;
    private final Integer errorCode;
    private final String description;

    }