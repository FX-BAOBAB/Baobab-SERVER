package delivery.common.error;

import global.errorcode.ErrorCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ShippingErrorCode implements ErrorCodeIfs {

    SHIPPING_REQUEST_NOT_FOUND(HttpStatus.NOT_FOUND.value(), 1500, "출고 요청서가 존재하지 않습니다."),
    SHIPPING_NOT_IN_PENDING(HttpStatus.NOT_FOUND.value(),1501,"출고 요청 상태가 아닙니다."),
    SHIPPING_NOT_IN_REGISTERED(HttpStatus.NOT_FOUND.value(),1502,"출고 접수 상태가 아닙니다."),
    SHIPPING_NOT_IN_DELIVERY(HttpStatus.NOT_FOUND.value(),1503,"출고 배송 상태가 아닙니다."),
    SHIPPING_NOT_IN_READY(HttpStatus.NOT_FOUND.value(),1504,"출고 배송 준비 상태가 아닙니다.");

    private final Integer httpCode;
    private final Integer errorCode;
    private final String description;
}
