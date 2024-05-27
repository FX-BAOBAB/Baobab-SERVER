package warehouse.domain.token.exception;

import global.api.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import warehouse.domain.token.errorcode.TokenErrorCode;
import warehouse.domain.token.exception.tokenexception.ExpireAccessTokenException;
import warehouse.domain.token.exception.tokenexception.AuthorizationTokenNotFoundException;
import warehouse.domain.token.exception.tokenexception.InvalidTokenException;
import warehouse.domain.token.exception.tokenexception.ExpireRefreshTokenException;
import warehouse.domain.token.exception.tokenexception.UnknownTokenException;
import warehouse.domain.token.exception.tokenexception.ValidTokenException;

@RequiredArgsConstructor
@RestControllerAdvice
@Slf4j
public class TokenExceptionAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidTokenException.class)
    public Api<Object> InvalidTokenException() {
        log.info("유효하지 않은 토큰입니다.");
        return Api.ERROR(TokenErrorCode.INVALID_TOKEN);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ExpireRefreshTokenException.class)
    public Api<Object> expireRefreshTokenException() {
        log.info("만료된 RefreshToken 입니다.");
        return Api.ERROR(TokenErrorCode.EXPIRE_TOKEN);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ExpireAccessTokenException.class)
    public Api<Object> accessTokenException() {
        log.info("만료된 AccessToken 입니다.");
        return Api.ERROR(TokenErrorCode.EXPIRE_TOKEN);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UnknownTokenException.class)
    public Api<Object> unknownTokenException() {
        log.info("알 수 없는 토큰입니다.");
        return Api.ERROR(TokenErrorCode.UNKNOWN_TOKEN_EXCEPTION);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AuthorizationTokenNotFoundException.class)
    public Api<Object> authorizationTokenNotFoundException() {
        log.info("인증 헤더 토큰이 없습니다.");
        return Api.ERROR(TokenErrorCode.AUTHORIZATION_TOKEN_NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidTokenException.class)
    public Api<Object> validTokenException() {
        log.info("만료되지 않은 토큰입니다.");
        return Api.ERROR(TokenErrorCode.VALID_TOKEN);
    }

}
