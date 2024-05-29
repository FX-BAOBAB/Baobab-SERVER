package warehouse.domain.address.exception;

import global.api.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import warehouse.domain.address.errorcode.AddressErrorCode;
import warehouse.domain.address.exception.addressexception.InvalidAddressDataException;
import warehouse.domain.address.exception.addressexception.NotFoundAddressException;

@RestControllerAdvice
@Slf4j
public class AddressExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotFoundAddressException.class)
    public Api<Object> notFoundAddressException() {
        log.info("주소 정보를 찾을 수 없습니다.");
        return Api.ERROR(AddressErrorCode.NOT_FOUND_ADDRESS);

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidAddressDataException.class)
    public Api<Object> InvalidAddressDataException() {
        log.info("잘못된 주소 데이터입니다.");
        return Api.ERROR(AddressErrorCode.INVALID_ADDRESS_DATA);
    }
}
