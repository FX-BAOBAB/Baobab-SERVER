package warehouse.domain.users.security.jwt.business;

import db.common.BaseEntity;
import db.domain.token.RefreshTokenEntity;
import db.domain.users.UserEntity;
import global.annotation.Business;
import global.errorcode.ErrorCode;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import warehouse.common.error.TokenErrorCode;
import warehouse.common.exception.jwt.TokenException;
import warehouse.common.exception.jwt.TokenSignatureException;
import warehouse.domain.users.security.jwt.converter.TokenConverter;
import warehouse.domain.users.security.jwt.model.TokenDto;
import warehouse.domain.users.security.jwt.model.TokenResponse;
import warehouse.domain.users.security.jwt.service.TokenService;

@Business
@RequiredArgsConstructor
public class TokenBusiness {

    private final TokenService tokenService;
    private final TokenConverter tokenConverter;

    @Transactional
    public TokenResponse issueToken(UserEntity userEntity) {

        return Optional.ofNullable(userEntity).map(BaseEntity::getId)
            .map(userId -> {
                TokenDto accessToken = tokenService.issueAccessToken(userId);
                TokenDto refreshToken = tokenService.issueRefreshToken(userId);
                RefreshTokenEntity refreshTokenEntity = tokenConverter.toRefreshTokenEntity(
                    userEntity.getId(), refreshToken.getToken());
                tokenService.deleteRefreshToken(userId);
                tokenService.saveRefreshToken(refreshTokenEntity);
                return tokenConverter.toResponse(accessToken, refreshToken);
            }).orElseThrow(() -> new TokenException(ErrorCode.NULL_POINT));
    }

    public TokenDto reIssueAccessToken(String refreshToken) {
        //JWT가 헤더에 있는 경우
        if (refreshToken != null && refreshToken.startsWith("Bearer ")) {
            String token = refreshToken.substring(7);
            return tokenService.reIssueAccessToken(token);
        }
        throw new TokenSignatureException(TokenErrorCode.INVALID_TOKEN);
    }
}
