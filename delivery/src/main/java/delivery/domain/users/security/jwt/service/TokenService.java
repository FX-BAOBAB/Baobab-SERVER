package delivery.domain.users.security.jwt.service;

import delivery.common.exception.jwt.TokenException;
import delivery.domain.users.security.jwt.helper.TokenHelperIfs;
import global.errorcode.ErrorCode;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenHelperIfs tokenHelperIfs;

    public Long validationToken(String token){

        Map<String, Object> map = tokenHelperIfs.validationTokenWithThrow(token);

        Object userId = map.get("userId");
        Objects.requireNonNull(userId,()->{throw new TokenException(ErrorCode.NULL_POINT);});

        return Long.parseLong(userId.toString());
    }

}
