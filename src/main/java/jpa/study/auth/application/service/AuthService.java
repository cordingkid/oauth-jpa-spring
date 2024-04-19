package jpa.study.auth.application.service;

import jpa.study.auth.application.domain.kakao.KakaoOauthClient;
import jpa.study.auth.application.domain.kakao.KakaoRedirectUriProvider;
import jpa.study.auth.application.dto.LoginResult;
import jpa.study.auth.infra.kakao.response.KakaoMember;
import jpa.study.auth.infra.kakao.response.KakaoTokenResponse;
import jpa.study.user.application.domain.User;
import jpa.study.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final KakaoRedirectUriProvider kakaoRedirectUriProvider;
    private final KakaoOauthClient kakaoOauthClient;
    private final UserRepository userRepository;

    public String provideRedirectUri() {
        return kakaoRedirectUriProvider.provide();
    }

    public LoginResult login(String authCode) {
        KakaoTokenResponse kakaoTokenResponse = kakaoOauthClient.requestToken(authCode);
        KakaoMember kakaoMember = kakaoOauthClient.fetchMember(kakaoTokenResponse.accessToken());

        Optional<User> foundUser = userRepository.findByOauthId(kakaoMember.id());
        return foundUser.map(user -> loginNonInit(user, kakaoTokenResponse.accessToken()))
                .orElseGet(() -> loginInit(
                        kakaoMember.id(),
                        kakaoMember.kakaoAccount().email(),
                        kakaoTokenResponse.accessToken()
                ));
    }

    private LoginResult loginNonInit(User user, String kakaoAccessToken) {
        if (user.isDelete()) {
            throw new RuntimeException("삭제된 유저 입니다.");
        }
        return null;
    }

    private LoginResult loginInit(Long kakaoId, String kakaoEmail, String kakaoAccessToken) {
        User user = User.init(kakaoId, kakaoEmail, kakaoAccessToken);
        User createdUser = userRepository.save(user);

        return null;
    }
}
