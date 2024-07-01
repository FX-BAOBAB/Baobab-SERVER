package warehouse.domain.users.security.jwt.helper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import warehouse.common.error.TokenErrorCode;
import warehouse.common.exception.jwt.TokenException;
import warehouse.common.exception.jwt.TokenExpiredException;
import warehouse.common.exception.jwt.TokenSignatureException;
import warehouse.domain.users.security.jwt.ifs.TokenHelperIfs;
import warehouse.domain.users.security.jwt.model.TokenDto;

@Slf4j
@Component
public class JwtTokenHelper implements TokenHelperIfs {

    @Value("${jwt.secret.key}")
    private String secretKey;
    @Value("${jwt.access-token.plus-hour}")
    private Long accessTokenPlusHour;
    @Value("${jwt.refresh-token.plus-hour}")
    private Long refreshTokenPlusHour;

    @Override
    public TokenDto issueAccessToken(Map<String, Object> data) {
        return getTokenDto(data, accessTokenPlusHour);
    }

    @Override
    public TokenDto issueRefreshToken(Map<String, Object> data) {
        return getTokenDto(data, refreshTokenPlusHour);
    }

    @Override
    public Map<String, Object> validationTokenWithThrow(String token) {

        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        JwtParser parser = Jwts.parserBuilder().setSigningKey(key).build();

        try{
            Jws<Claims> result = parser.parseClaimsJws(token);
            return new HashMap<>(result.getBody());
        }catch (Exception e){
            if (e instanceof SignatureException){
                // 토큰 유효하지 않음
                throw new TokenSignatureException(TokenErrorCode.INVALID_TOKEN,e);
            }else if (e instanceof ExpiredJwtException){
                // 토큰 만료
                throw new TokenExpiredException(TokenErrorCode.EXPIRED_TOKEN,e);
            }else {
                // 그 외
                throw new TokenException(TokenErrorCode.TOKEN_EXCEPTION,e);
            }
        }
    }

    private TokenDto getTokenDto(Map<String, Object> data, Long refreshTokenPlusHour) {
        LocalDateTime expiredLocalDateTime = LocalDateTime.now().plusHours(refreshTokenPlusHour);
        Date expiredAt = Date.from(expiredLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());

        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        String jwtToken = Jwts.builder()
            .signWith(key, SignatureAlgorithm.HS256)
            .setClaims(data)
            .setExpiration(expiredAt)
            .compact();

        return TokenDto.builder()
            .token(jwtToken)
            .expiredAt(expiredLocalDateTime)
            .build();
    }
}
