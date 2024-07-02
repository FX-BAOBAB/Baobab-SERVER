package warehouse.domain.users.security.jwt.service;

import db.domain.token.RefreshTokenEntity;
import db.domain.token.RefreshTokenRepository;
import global.errorcode.ErrorCode;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import warehouse.common.error.TokenErrorCode;
import warehouse.common.exception.ApiException;
import warehouse.common.exception.jwt.TokenException;
import warehouse.domain.users.security.jwt.ifs.TokenHelperIfs;
import warehouse.domain.users.security.jwt.model.TokenDto;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenHelperIfs tokenHelperIfs;
    private final RefreshTokenRepository refreshTokenRepository;

    public TokenDto issueAccessToken(Long userId){
        Map<String, Object> data = new HashMap<>();
        data.put("userId",userId);
        return tokenHelperIfs.issueAccessToken(data);
    }

    public TokenDto issueRefreshToken(Long userId){
        Map<String, Object> data = new HashMap<>();
        data.put("userId",userId);
        return tokenHelperIfs.issueRefreshToken(data);
    }

    public Long validationToken(String token){

        Map<String, Object> map = tokenHelperIfs.validationTokenWithThrow(token);

        Object userId = map.get("userId");
        Objects.requireNonNull(userId,()->{throw new ApiException(ErrorCode.NULL_POINT);});

        return Long.parseLong(userId.toString());
    }

    public void saveRefreshToken(RefreshTokenEntity refreshTokenEntity) {
        refreshTokenRepository.save(refreshTokenEntity);
    }

    public void deleteRefreshToken(Long userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }

    public TokenDto reIssueAccessToken(String refreshToken) {
        Long userId = validationToken(refreshToken);
        RefreshTokenEntity entity = refreshTokenRepository.findFirstByUserIdOrderByUserId(userId);
        if (!Objects.equals(userId, entity.getUserId())){
            throw new TokenException(TokenErrorCode.INVALID_TOKEN);
        }
        return issueRefreshToken(userId);
    }
}
