package jpa.study.common.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jpa.study.auth.application.domain.JwtManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;

import static org.springframework.http.HttpHeaders.*;
import static org.springframework.util.ObjectUtils.isEmpty;
import static org.springframework.util.StringUtils.*;

@Component
@RequiredArgsConstructor
public class TokenReader {

    private final JwtManager jwtManager;

    @Nullable
    public Long readAccessToken(HttpServletRequest request) {
        String authorization = request.getHeader(AUTHORIZATION);
        if (hasText(authorization)) {
            return jwtManager.readTokenWithPrefix(authorization);
        }

        Cookie[] cookies = request.getCookies();
        if (isEmpty(cookies)) {
            return null;
        }

        return Arrays.stream(cookies)
                .filter(cookie -> isTokenRelatedCookie(cookie.getName()))
                .findFirst()
                .map(cookie -> jwtManager.readTokenWithoutPrefix(cookie.getValue()))
                .orElse(null);
    }

    private boolean isTokenRelatedCookie(String cookieName) {
        return cookieName.equals("accessToken") || cookieName.equals("refreshToken");
    }
}
