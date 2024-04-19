package jpa.study.auth.infra.kakao;

import jpa.study.auth.infra.kakao.response.KakaoLogoutResponse;
import jpa.study.auth.infra.kakao.response.KakaoMember;
import jpa.study.auth.infra.kakao.response.KakaoTokenResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.PostExchange;

import static org.springframework.http.HttpHeaders.*;

public interface KakaoOauthApiClient {

    @PostExchange(
            url = "https://kauth.kakao.com/oauth/token",
            contentType = "application/x-www-form-urlencoded;charset=utf-8"
    )
    KakaoTokenResponse requestToken(@RequestBody MultiValueMap<String, String> parameters);

    @PostExchange(
            url = "https://kapi.kakao.com/v2/user/me",
            contentType = "application/x-www-form-urlencoded;charset=utf-8"
    )
    KakaoMember fetchKakaoMember(@RequestHeader(value = AUTHORIZATION) String accessToken);

    @PostExchange(url = "https://kapi.kakao.com/v1/user/logout")
    KakaoLogoutResponse logout(@RequestHeader(value = AUTHORIZATION) String accessToken);
}
