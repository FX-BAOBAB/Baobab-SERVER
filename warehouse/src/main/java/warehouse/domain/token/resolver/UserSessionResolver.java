package warehouse.domain.token.resolver;

import db.domain.account.AccountEntity;
import global.annotation.UserSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import warehouse.domain.users.model.User;
import warehouse.domain.users.service.UserService;

@Component
@RequiredArgsConstructor
public class UserSessionResolver implements HandlerMethodArgumentResolver {

    private final UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        /**
         * 지원하는 파라미터 체크
         * 1. 어노테이션 체크
         * 2. 파라미터 타입 체크
         */

        boolean annotation = parameter.hasParameterAnnotation(UserSession.class);
        boolean parameterType = parameter.getParameterType().equals(User.class);

        return (annotation && parameterType);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        //support parameter 에서 true 반환시 실행

        //request context holder에서 찾아오기
        RequestAttributes requestContext = RequestContextHolder.getRequestAttributes();
        Object userId = requestContext.getAttribute("userId", RequestAttributes.SCOPE_REQUEST);

        AccountEntity user = userService.getUser(Long.parseLong(userId.toString()));

        //사용자 정보 setting
        return User.builder()
            .id(user.getId())
            .userStatus(user.getUser().getStatus())
            .build();
    }
}
