package store.domain.image.domain.users.security.jwt.helper;

import java.util.Map;
import store.domain.image.domain.users.security.jwt.model.TokenDto;

public interface TokenHelperIfs {

    TokenDto issueAccessToken(Map<String,Object> data);
    TokenDto issueRefreshToken(Map<String,Object> data);
    Map<String, Object> validationTokenWithThrow(String token);

}
