package jpa.study.auth.presentation;

import jakarta.servlet.http.HttpServletResponse;
import jpa.study.auth.application.dto.LoginResult;
import jpa.study.auth.application.service.AuthService;
import jpa.study.auth.presentation.dto.LoginResponse;
import jpa.study.common.util.TimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;
import static jpa.study.common.util.TimeUtils.convertTimeUnit;
import static org.springframework.http.HttpHeaders.SET_COOKIE;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/auth/kakao")
    ResponseEntity<Void> redirectAuthCodeRequestUrl(HttpServletResponse response) throws IOException {
        String requestUrl = authService.provideRedirectUri();
        response.sendRedirect(requestUrl);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/login/oauth2/code/kakao")
    ResponseEntity<LoginResponse> login(@RequestParam("code") String authCode) {
        LoginResult loginResult = authService.login(authCode);

        ResponseCookie accessTokenCookie = createCookie(
                "accessToken",
                loginResult.accessToken(),
                Duration.ofSeconds(
                        convertTimeUnit(
                                loginResult.accessTokenValidTimeDuration(),
                                loginResult.accessTokenValidTimeUnit(),
                                SECONDS
                        )
                ), true, true, "sameSite"
        );

        ResponseCookie refreshTokenCookie = createCookie(
                "refreshToken",
                loginResult.refreshToken(),
                Duration.ofSeconds(
                        TimeUtils.convertTimeUnit(loginResult.refreshTokenValidTimeDuration(),
                                loginResult.refreshTokenValidTimeUnit(),
                                SECONDS)
                ), true, true, "sameSite"
        );

        LoginResponse response = LoginResponse.of(loginResult);

        return ResponseEntity.ok()
                .header(SET_COOKIE, accessTokenCookie.toString())
                .header(SET_COOKIE, refreshTokenCookie.toString())
                .body(response);
    }

//    @PatchMapping("/auth/kakao")
//    ResponseEntity<Void> logout()

    private ResponseCookie createCookie(
            String name,
            String value,
            Duration maxAge,
            boolean httpOnly,
            boolean secure,
            String sameSite
    ) {
        return ResponseCookie.from(name)
                .value(value)
                .maxAge(maxAge)
                .httpOnly(httpOnly)
                .secure(secure)
                .sameSite(sameSite)
                .build();
    }
}
