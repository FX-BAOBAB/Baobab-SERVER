package delivery.common.exception;

import delivery.common.error.UserErrorCode;
import delivery.common.exception.user.AddressNotFoundException;
import delivery.common.exception.user.ExistUserException;
import delivery.common.exception.user.FailedToRegisterException;
import delivery.common.exception.user.LogInException;
import delivery.common.exception.user.UserNotFoundException;
import delivery.common.exception.user.UserUnregisterException;
import global.api.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(value = ExistUserException.class)
    public ResponseEntity<Api<Object>> existUserException(ExistUserException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(Api.ERROR(UserErrorCode.EXIST_USER));
    }

    @ExceptionHandler(FailedToRegisterException.class)
    public ResponseEntity<Api<Object>> failedToRegisterException(FailedToRegisterException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Api.ERROR(UserErrorCode.FAILED_TO_REGISTER));
    }

    @ExceptionHandler(value = LogInException.class)
    public ResponseEntity<Api<Object>> logInException(LogInException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(Api.ERROR(UserErrorCode.LOGIN_FAIL));
    }

    @ExceptionHandler(value = AddressNotFoundException.class)
    public ResponseEntity<Api<Object>> addressNotFoundException(AddressNotFoundException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(Api.ERROR(UserErrorCode.ADDRESS_NOT_FOUND));
    }

    @ExceptionHandler(value = UserUnregisterException.class)
    public ResponseEntity<Api<Object>> userUnregisterException(UserUnregisterException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Api.ERROR(UserErrorCode.FAILED_TO_UNREGISTER));
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Api<Object>> userNotFoundException(UserNotFoundException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(Api.ERROR(UserErrorCode.USER_NOT_FOUND));
    }

}
