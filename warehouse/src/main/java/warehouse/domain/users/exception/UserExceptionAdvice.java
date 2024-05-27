package warehouse.domain.users.exception;

import global.api.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import warehouse.domain.users.errorcode.UserErrorCode;
import warehouse.domain.users.exception.userexception.UserNotFoundException;

@RestControllerAdvice
@Slf4j
public class UserExceptionAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserNotFoundException.class)
    public Api<Object> UserNotFoundException() {
        log.info("존재하지 않는 사용자입니다.");
        return Api.ERROR(UserErrorCode.NOT_FOUND_USER);
    }


}
