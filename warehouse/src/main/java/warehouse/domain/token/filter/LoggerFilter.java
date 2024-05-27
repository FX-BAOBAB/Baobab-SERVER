package warehouse.domain.token.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Slf4j
@Component
public class LoggerFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
        FilterChain chain) throws IOException, ServletException {

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(
            (HttpServletRequest) request);

        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(
            (HttpServletResponse) response);

        chain.doFilter(request, response);

        //request 정보
        Enumeration<String> headerNames = requestWrapper.getHeaderNames();
        StringBuilder requestHeaderValues = new StringBuilder();

        headerNames.asIterator().forEachRemaining(headerKey -> {
            String header = requestWrapper.getHeader(headerKey);

            //authorization-token : ???, user-agent : ???
            requestHeaderValues
                .append("[")
                .append(headerKey)
                .append(" : ")
                .append(header)
                .append("] ");
        });

        String requestBody = new String(requestWrapper.getContentAsByteArray());
        String uri = requestWrapper.getRequestURI();
        String method = requestWrapper.getMethod();

        log.info(">>>>> uri:+ : {}, method : {}, header : {}, body : {}", uri, method, requestHeaderValues, requestBody);

        //response 정보
        StringBuilder responseHeaderValues = new StringBuilder();

        responseWrapper.getHeaderNames().forEach(headerKey -> {
            String header = responseWrapper.getHeader(headerKey);

            responseHeaderValues
                .append("[")
                .append(headerKey)
                .append(" : ")
                .append(header)
                .append("] ");
        });

        String responseBody = new String(responseWrapper.getContentAsByteArray());

        log.info("<<<<< uri:+ : {}, method : {}, header : {}, body : {}", uri, method, responseHeaderValues, responseBody);

        responseWrapper.copyBodyToResponse();

    }
}
