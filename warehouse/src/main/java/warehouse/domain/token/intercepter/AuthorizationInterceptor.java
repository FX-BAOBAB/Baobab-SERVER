package warehouse.domain.token.intercepter;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import warehouse.domain.token.business.TokenBusiness;
import warehouse.domain.token.exception.tokenexception.AuthorizationTokenNotFoundException;
import warehouse.domain.token.exception.tokenexception.InvalidTokenException;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final static String USER_ID = "userId";
    private final static String AUTHORIZATION_TOKEN = "authorization-token";

    private final TokenBusiness tokenBusiness;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {
        log.info("Authorization Interceptor url : {}", request.getRequestURI());

        /**
         * WEB, chrome 의 경우 GET, POST OPTION -> pass
         */
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }

        /**
         * js, html, png resource 요청인 경우 -> pass
         */
        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }

        /**
         * 토큰 검증
         */
        var accessToken = request.getHeader(AUTHORIZATION_TOKEN);
        if (accessToken == null) {
            throw new AuthorizationTokenNotFoundException();
        }

        Long userId = tokenBusiness.validationAccessToken(accessToken);

        if (userId != null) {
            RequestAttributes requestContext = Objects.requireNonNull(
                RequestContextHolder.getRequestAttributes());
            requestContext.setAttribute(USER_ID, userId, RequestAttributes.SCOPE_REQUEST);
            return true;
        }

        throw new InvalidTokenException();
    }
}