package warehouse.common.error;

import global.errorcode.ErrorCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GoodsErrorCode implements ErrorCodeIfs {

    GOODS_NOT_FOUND(HttpStatus.NOT_FOUND.value(), 1200, "물품이 존재하지 않습니다."),
    INVALID_GODOS_STATUS(HttpStatus.BAD_REQUEST.value(), 1201, "다음 작업을 진행할 수 없는 상태입니다.")
    ;

    private final Integer httpCode;
    private final Integer errorCode;
    private final String description;

}
