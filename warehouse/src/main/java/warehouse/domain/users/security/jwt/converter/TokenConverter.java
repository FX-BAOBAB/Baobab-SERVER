package warehouse.domain.users.security.jwt.converter;

import db.domain.token.RefreshTokenEntity;
import global.annotation.Converter;
import global.errorcode.ErrorCode;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import warehouse.common.exception.jwt.TokenException;
import warehouse.domain.users.security.jwt.model.TokenDto;
import warehouse.domain.users.security.jwt.model.TokenResponse;

@Converter
@RequiredArgsConstructor
public class TokenConverter {

    public TokenResponse toResponse(
        TokenDto accessToken,
        TokenDto refreshToken
    ){

        Objects.requireNonNull(accessToken, ()->{throw new TokenException(ErrorCode.NULL_POINT);});
        Objects.requireNonNull(refreshToken, ()->{throw new TokenException(ErrorCode.NULL_POINT);});

        return TokenResponse.builder()
            .accessToken(accessToken.getToken())
            .accessTokenExpiredAt(accessToken.getExpiredAt())
            .refreshToken(refreshToken.getToken())
            .refreshTokenExpiredAt(refreshToken.getExpiredAt())
            .build();
    }

    public RefreshTokenEntity toRefreshTokenEntity(Long userId, String refreshToken ) {
        return RefreshTokenEntity.builder()
            .userId(userId)
            .refreshToken(refreshToken)
            .build();
    }
}
