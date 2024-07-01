package warehouse.domain.users.security.jwt.ifs;

import java.util.Map;
import warehouse.domain.users.security.jwt.model.TokenDto;

public interface TokenHelperIfs {

    TokenDto issueAccessToken(Map<String,Object> data);
    TokenDto issueRefreshToken(Map<String,Object> data);
    Map<String, Object> validationTokenWithThrow(String token);

}
