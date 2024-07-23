package warehouse.common.exception;

import global.api.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import warehouse.common.error.ShippingErrorCode;
import warehouse.common.exception.shipping.ShippingNotFoundException;

@Slf4j
@RestControllerAdvice
public class ShippingExceptionHandler {

    @ExceptionHandler(value = ShippingNotFoundException.class)
    public ResponseEntity<Api<Object>> shippingNotFoundException(ShippingNotFoundException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(Api.ERROR(ShippingErrorCode.SHIPPING_REQUEST_NOT_FOUND));
    }

}
