package warehouse.domain.token.config.web;

import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import warehouse.domain.token.intercepter.AuthorizationInterceptor;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import warehouse.domain.token.resolver.UserSessionResolver;

@Component
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthorizationInterceptor authorizationInterceptor;

    private final UserSessionResolver userSessionResolver;

    private List<String> OPEN_API = List.of(
        "/open-api/**"
    );

    private List<String> DEFAULT_EXCLUDE = List.of(
        "/",
        "favicon.ico",
        "/error"
    );

    private List<String> SWAGGER = List.of(
        "/swagger-ui.html",
        "/swagger-ui/**",
        "/v3/api-docs/**"
    );


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor)
            .excludePathPatterns(OPEN_API)
            .excludePathPatterns(DEFAULT_EXCLUDE)
            .excludePathPatterns(SWAGGER)
        ;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
        resolvers.add(userSessionResolver);
    }
}
