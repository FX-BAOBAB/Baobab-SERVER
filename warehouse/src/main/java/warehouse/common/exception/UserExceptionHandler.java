package warehouse.common.exception;

import global.api.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import warehouse.common.error.UserErrorCode;
import warehouse.common.exception.user.ExistUserException;
import warehouse.common.exception.user.FailedToRegisterException;
import warehouse.common.exception.user.LogInException;

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
}
