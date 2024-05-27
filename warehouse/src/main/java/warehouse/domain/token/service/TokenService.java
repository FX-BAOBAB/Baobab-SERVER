package warehouse.domain.token.service;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import warehouse.domain.token.exception.tokenexception.ExpireAccessTokenException;
import warehouse.domain.token.exception.tokenexception.UnknownTokenException;
import warehouse.domain.users.exception.userexception.UserNotFoundException;
import warehouse.domain.token.ifs.TokenHelperIfs;
import warehouse.domain.token.model.TokenDto;

@RequiredArgsConstructor
@Service
@Slf4j
public class TokenService {

    private final TokenHelperIfs tokenHelperIfs;

    public TokenDto issueAccessToken(Long userId) {
        Map<String, Object> data = new ConcurrentHashMap<>();
        data.put("userId", userId);
        return tokenHelperIfs.issueAccessToken(data);
    }

    public TokenDto issueRefreshToken(Long userId) {
        Map<String, Object> data = new ConcurrentHashMap<>();
        data.put("userId", userId);
        return tokenHelperIfs.issueRefreshToken(data);
    }

}
