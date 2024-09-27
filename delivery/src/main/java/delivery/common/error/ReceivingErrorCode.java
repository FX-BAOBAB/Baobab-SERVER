package delivery.common.error;

import global.errorcode.ErrorCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ReceivingErrorCode implements ErrorCodeIfs {

    RECEIVING_REQUEST_NOT_FOUND(HttpStatus.NOT_FOUND.value(), 1250, "입고 요청서가 존재하지 않습니다."),
    NO_OWNERSHIP(HttpStatus.BAD_REQUEST.value(), 1251, "사용자의 물품이 아닙니다."),
    RECEIVING_NOT_IN_TAKING(HttpStatus.BAD_REQUEST.value(), 1252,"입고 요청 상태가 아닙니다."),
    RECEIVING_NOT_IN_CONFIRMATION(HttpStatus.NOT_FOUND.value(),1253,"입고 확정 상태가 아닙니다."),
    RECEIVING_NOT_IN_DELIVERY(HttpStatus.NOT_FOUND.value(),1254,"입고 배송 상태가 아닙니다."),
    RECEIVING_NOT_IN_REGISTERED(HttpStatus.NOT_FOUND.value(),1255,"입고 접수 상태가 아닙니다."),
    RECEIVING_NOT_IN_CHECKING(HttpStatus.NOT_FOUND.value(),1256,"입고 심의 상태가 아닙니다.");

    private final Integer httpCode;
    private final Integer errorCode;
    private final String description;
}