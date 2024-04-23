package jpa.study.common.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;

import static org.springframework.http.HttpMethod.*;

@Component
@RequiredArgsConstructor
public class AuthorizationInterceptor implements HandlerInterceptor {

    private static final UriAndMethod[] WHITE_LIST = {
            new UriAndMethod("/auth/kakao", GET),
            new UriAndMethod("/login/oauth2/code/kakao", GET),
            new UriAndMethod("/hello", GET),
    };

    private final AuthContext authContext;
    private final TokenReader tokenReader;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (isPreflight(request)) {
            return true;
        }
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        if (isNonRequiredAuthentication(handler)) {
            return true;
        }

        Long userId = tokenReader.readAccessToken(request);
        authContext.setUserId(userId);
        return true;
    }

    private boolean isPreflight(HttpServletRequest request) {
        return request.getMethod().equals(OPTIONS.name());
    }

    private boolean isNonRequiredAuthentication(Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        RequestMapping requestMapping = handlerMethod.getMethodAnnotation(RequestMapping.class);

        // 매핑된 Uri
        String mappingUri = requestMapping.value()[0];

        // 매핑된 매소드
        HttpMethod mappingMethod = requestMapping.method()[0].asHttpMethod();

        // 검사
        return Arrays.stream(WHITE_LIST)
                .anyMatch(it -> it.isMatch(mappingUri, mappingMethod));
    }
}