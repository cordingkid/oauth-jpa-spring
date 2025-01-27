package jpa.study.auth.application.domain.kakao;

import jpa.study.auth.infra.kakao.KakaoOauthApiClient;
import jpa.study.auth.infra.kakao.response.KakaoLogoutResponse;
import jpa.study.auth.infra.kakao.response.KakaoMember;
import jpa.study.auth.infra.kakao.response.KakaoTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Component
@RequiredArgsConstructor
public class KakaoOauthClient {

    private static final String TOKEN_TYPE = "Bearer ";
    private final KakaoOauthConfig kakaoOauthConfig;
    private final KakaoOauthApiClient apiClient;

    public KakaoTokenResponse requestToken(String authCode) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>() {{
            add("grant_type", "authorization_code");
            add("client_id", kakaoOauthConfig.clientId());
            add("redirect_uri", kakaoOauthConfig.redirectUri());
            add("code", authCode);
            add("client_secret", kakaoOauthConfig.clientSecret());
        }};

        return apiClient.requestToken(parameters);
    }

    public KakaoMember fetchMember(String accessToken) {
        return apiClient.fetchKakaoMember(TOKEN_TYPE + accessToken);
    }

    public Long logout(String kakaoAccessToken) {
        String accessToken = TOKEN_TYPE + kakaoAccessToken;
        KakaoLogoutResponse response = apiClient.logout(accessToken);
        return response.id();
    }
}
