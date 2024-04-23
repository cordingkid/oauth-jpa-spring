package jpa.study.common.auth;

import jpa.study.common.exception.CustomException;
import jpa.study.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static jpa.study.common.exception.ErrorCode.*;

/**
 * [동작 시점]
 * Argument Resolver는 Interceptor 가 처리된 후 처리된다.
 *
 * 1. Client 요청(Request).
 * 2. Dispatcher Servlet에서 Request를 처리
 * 3. 요청을 분석하여 Request에 대한 Hadler Mapping
 *  - RequestMappingHandlerAdapter(핸들러 매핑에 맞는 어댑터 결정).
 *  - Interceptor 처리
 *  - Argument Resolver 처리 (등록한 리졸버에 대응되는 파라미터 바인딩)
 *  - Message Converter 처리
 * 4. Controller Method invoke
 */
@Component
@RequiredArgsConstructor
public class AuthArgumentResolver implements HandlerMethodArgumentResolver {

    private final AuthContext authContext;

    /**
     * parameter가 해당 resolver를 지원하는 여부 확인
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Auth.class) &&
                parameter.getParameterType().equals(Long.class);
    }

    /**
     * Method parameter를 argument value로 변환, 바인딩 하는 역할
     */
    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) throws Exception {
        Long userId = authContext.getUserId();
        if (userId == null) {
            throw new CustomException(REQUIRED_ACCESS_TOKEN);
        }
        return userId;
    }
}
