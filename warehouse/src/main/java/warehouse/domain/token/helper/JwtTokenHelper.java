package warehouse.domain.token.helper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import warehouse.domain.token.exception.tokenexception.InvalidTokenException;
import warehouse.domain.token.exception.tokenexception.ExpireRefreshTokenException;
import warehouse.domain.token.exception.tokenexception.ExpireAccessTokenException;
import warehouse.domain.token.exception.tokenexception.UnknownTokenException;
import warehouse.domain.token.exception.tokenexception.ValidTokenException;
import warehouse.domain.token.ifs.TokenHelperIfs;
import warehouse.domain.token.model.TokenDto;

@Component
@Slf4j
public class JwtTokenHelper implements TokenHelperIfs {

    @Value("${token.secret.key}")
    private String secretKey;

    @Value("${token.expiration-time.access}")
    private Long accessExpirationTime;

    @Value("${token.expiration-time.refresh}")
    private Long refreshExpirationTime;


    @Override
    public TokenDto issueAccessToken(Map<String, Object> data) {

        /**
         * access 5분
         */
        LocalDateTime expiredLocalDateTime = LocalDateTime.now().plusMinutes(accessExpirationTime);

        /**
         * 시스템의 기본 시간대를 사용
         */
        Date expiredAt = Date.from(expiredLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());

        /**
         * HMAC-SHA 알고리즘에 사용할 비밀 키 생성
         */
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

        //TODO 메서드 따로 빼기
        /**
         * JWT 토큰 생성
         */
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

    @Override
    public TokenDto issueRefreshToken(Map<String, Object> data) {

        /**
         * refresh 5분
         */
        LocalDateTime expiredLocalDateTime = LocalDateTime.now().plusMinutes(refreshExpirationTime);

        /**
         * 시스템의 기본 시간대를 사용
         */
        Date expiredAt = Date.from(expiredLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());

        /**
         * HMAC-SHA 알고리즘에 사용할 비밀 키 생성
         */
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

        //TODO  메서드 따로 빼기
        /**
         * JWT 토큰 생성
         */
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

    @Override
    public Map<String, Object> validationAccessTokenWithThrow(String token) {
        /**
         * HMAC-SHA 알고리즘에 사용할 비밀 키 생성
         */
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

        /**
         * 토큰 검증, 해석
         */
        JwtParser parser = Jwts.parserBuilder()
            .setSigningKey(key)
            .build();

        /**
         * claims 정보 추출
         * 토큰이 유효하지 않거나 서명이 올바르지 않은 경우 예외 발생
         */

        try {
            Jws<Claims> claimsJws = parser.parseClaimsJws(token);
            return new HashMap<String, Object>(claimsJws.getBody());
        } catch (Exception e) {
            if (e instanceof SignatureException) {
                //토큰이 유효하지 않을 때
                throw new InvalidTokenException(e);
            } else if (e instanceof ExpiredJwtException) {
                //만료된 토큰
                throw new ExpireAccessTokenException(e);
            } else {
                // 그외 에러
                throw new UnknownTokenException(e);
            }
        }
    }

    @Override
    public Map<String, Object> validationRefreshTokenWithThrow(String token) {
        /**
         * HMAC-SHA 알고리즘에 사용할 비밀 키 생성
         */
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

        /**
         * 토큰 검증, 해석
         */
        JwtParser parser = Jwts.parserBuilder()
            .setSigningKey(key)
            .build();

        /**
         * claims 정보 추출
         * 토큰이 유효하지 않거나 서명이 올바르지 않은 경우 예외 발생
         */

        try {
            Jws<Claims> claimsJws = parser.parseClaimsJws(token);
            return new HashMap<String, Object>(claimsJws.getBody());
        } catch (Exception e) {
            if (e instanceof SignatureException) {
                //토큰이 유효하지 않을 때
                throw new InvalidTokenException(e);
            } else if (e instanceof ExpiredJwtException) {
                //만료된 토큰
                throw new ExpireRefreshTokenException(e);
            } else {
                // 그외 에러
                throw new UnknownTokenException(e);
            }
        }
    }

    @Override
    public TokenDto validationAndReIssueAccessToken(String accessToken, Map<String, Object> data) {

        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

        /**
         * 토큰 검증, 해석
         */
        JwtParser parser = Jwts.parserBuilder()
            .setSigningKey(key)
            .build();

        /**
         * claims 정보 추출
         * 토큰이 유효하지 않거나 서명이 올바르지 않은 경우 예외 발생
         */

        try {
            Jws<Claims> claimsJws = parser.parseClaimsJws(accessToken);
        } catch (Exception e) {
            if (e instanceof SignatureException) {
                //토큰이 유효하지 않을 때
                throw new InvalidTokenException(e);
            } else if (e instanceof ExpiredJwtException) {
                //만료된 토큰
                /**
                 * access 5분
                 */
                LocalDateTime expiredLocalDateTime = LocalDateTime.now()
                    .plusMinutes(accessExpirationTime);

                /**
                 * 시스템의 기본 시간대를 사용
                 */
                Date expiredAt = Date.from(
                    expiredLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());

                /**
                 * HMAC-SHA 알고리즘에 사용할 비밀 키 생성
                 */

                //TODO 메서드 따로 빼기
                /**
                 * JWT 토큰 생성
                 */
                String jwtToken = Jwts.builder()
                    .signWith(key, SignatureAlgorithm.HS256)
                    .setClaims(data)
                    .setExpiration(expiredAt)
                    .compact();

                return TokenDto.builder()
                    .token(jwtToken)
                    .expiredAt(expiredLocalDateTime)
                    .build();
            } else {
                // 그외 에러
                throw new UnknownTokenException(e);
            }
        }

        throw new ValidTokenException();
    }
}
