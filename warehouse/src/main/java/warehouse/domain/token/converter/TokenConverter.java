package warehouse.domain.token.converter;

import global.annotation.Converter;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import warehouse.domain.token.exception.tokenexception.InvalidTokenException;
import warehouse.domain.users.controller.model.LoginResponse;
import warehouse.domain.token.model.TokenDto;

@RequiredArgsConstructor
@Converter
public class TokenConverter {

    public LoginResponse toResponse(TokenDto accessToken, TokenDto refreshToken) {

        /**
         * accessToken, refreshToken이 Null인 경우 예외 발생
         */
        Objects.requireNonNull(accessToken, () -> {
            throw new InvalidTokenException();
        });

        Objects.requireNonNull(refreshToken, () -> {
            throw new InvalidTokenException();
        });

        return LoginResponse.builder()
            .accessToken(accessToken.getToken())
            .expiredAt(accessToken.getExpiredAt())
            .refreshToken(refreshToken.getToken())
            .build();
    }
}
