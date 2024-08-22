package delivery.domain.users.security.jwt.helper;

import delivery.domain.users.security.jwt.model.TokenDto;
import java.util.Map;

public interface TokenHelperIfs {

    TokenDto issueAccessToken(Map<String,Object> data);
    TokenDto issueRefreshToken(Map<String,Object> data);
    Map<String, Object> validationTokenWithThrow(String token);

}
