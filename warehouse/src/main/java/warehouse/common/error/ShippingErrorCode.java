package warehouse.common.error;

import global.errorcode.ErrorCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ShippingErrorCode implements ErrorCodeIfs {

    SHIPPING_REQUEST_NOT_FOUND(HttpStatus.NOT_FOUND.value(), 1500, "출고 요청서가 존재하지 않습니다.")
    ;

    private final Integer httpCode;
    private final Integer errorCode;
    private final String description;
}
