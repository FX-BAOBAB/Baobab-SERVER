package warehouse.domain.token.ifs;

import java.util.Map;
import warehouse.domain.token.model.TokenDto;

public interface TokenHelperIfs {

    TokenDto issueAccessToken(Map<String, Object> data);

    TokenDto issueRefreshToken(Map<String, Object> data);

    Map<String, Object> validationAccessTokenWithThrow(String token);

    Map<String, Object> validationRefreshTokenWithThrow(String token);

    TokenDto validationAndReIssueAccessToken(String accessToken, Map<String, Object> data);
}
