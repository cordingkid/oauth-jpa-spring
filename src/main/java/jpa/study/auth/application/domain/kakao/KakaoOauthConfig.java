package jpa.study.auth.application.domain.kakao;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth.kakao")
public record KakaoOauthConfig(
        String clientId,
        String clientSecret,
        String redirectUri,
        String scope
) {
}
