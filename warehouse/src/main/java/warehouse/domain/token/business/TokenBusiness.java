package warehouse.domain.token.business;

import db.domain.account.AccountEntity;
import global.annotation.Business;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import warehouse.domain.token.controller.model.ReIssueAccessTokenResponse;
import warehouse.domain.token.converter.TokenConverter;
import warehouse.domain.token.model.TokenDto;
import warehouse.domain.token.service.TokenService;
import warehouse.domain.users.controller.model.LoginResponse;
import warehouse.domain.users.exception.userexception.UserNotFoundException;

@RequiredArgsConstructor
@Business
@Slf4j
public class TokenBusiness {

    private final TokenService tokenService;

    private final TokenConverter tokenConverter;

    /**
     * 토큰 발행
     */
    public LoginResponse issueToken(AccountEntity entity) {
        /**
         * 1. accountEntity에서 userId 추출
         * 2. accessToken, refreshToken 발행
         * 3. userId가 null인경우 예외 발생
         */

        // TODO Exception 처리
        return Optional.ofNullable(entity).map(accountEntity -> accountEntity.getUserId())
            .map(userId -> {
                TokenDto accessToken = tokenService.issueAccessToken(userId);
                TokenDto refreshToken = tokenService.issueRefreshToken(userId);
                return tokenConverter.toResponse(accessToken, refreshToken);
            }).orElseThrow(UserNotFoundException::new);
    }


    public Long validationAccessToken(String accessToken) {
        return tokenService.validationAccessToken(accessToken);
    }

    public ReIssueAccessTokenResponse reIssueAccessToken(String accessToken, String refreshToken) {

        // refreshToken 유효성 및 만료 확인
        Long userId = tokenService.validationRefreshToken(refreshToken);

        // accessToken 토큰 재발급
        TokenDto newAccessToken = tokenService.validationAndReIssueAccessToken(accessToken, userId);

        //5. 클라이언트에게 새 AccessToken 전달
        return ReIssueAccessTokenResponse.builder().accessToken(newAccessToken.getToken())
            .expiredAt(newAccessToken.getExpiredAt()).build();
    }

}
