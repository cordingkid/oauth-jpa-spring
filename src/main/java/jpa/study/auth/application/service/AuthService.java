package jpa.study.auth.application.service;

import jpa.study.auth.application.domain.JwtManager;
import jpa.study.auth.application.domain.kakao.KakaoOauthClient;
import jpa.study.auth.application.domain.kakao.KakaoRedirectUriProvider;
import jpa.study.auth.application.dto.LoginResult;
import jpa.study.auth.application.dto.ReissueTokenResult;
import jpa.study.auth.infra.kakao.response.KakaoMember;
import jpa.study.auth.infra.kakao.response.KakaoTokenResponse;
import jpa.study.common.exception.CustomException;
import jpa.study.common.exception.ErrorCode;
import jpa.study.user.application.domain.User;
import jpa.study.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static jpa.study.common.exception.ErrorCode.DELETED_USER_EXCEPTION;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final KakaoRedirectUriProvider kakaoRedirectUriProvider;
    private final KakaoOauthClient kakaoOauthClient;
    private final UserRepository userRepository;
    private final JwtManager jwtManager;

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
            throw new CustomException(DELETED_USER_EXCEPTION);
        }

        String accessToken = jwtManager.createAccessToken(user.getId());
        String refreshToken = jwtManager.createRefreshToken(user.getId());

        return LoginResult.of(
                user,
                false,
                accessToken,
                jwtManager.getAccessTokenValidTimeDuration(),
                jwtManager.getAccessTokenValidTimeUnit(),
                refreshToken,
                jwtManager.getRefreshTokenValidTimeDuration(),
                jwtManager.getRefreshTokenValidTimeUnit()
        );
    }

    private LoginResult loginInit(Long kakaoId, String kakaoEmail, String kakaoAccessToken) {
        User user = User.init(kakaoId, kakaoEmail, kakaoAccessToken);
        User createdUser = userRepository.save(user);
        String accessToken = jwtManager.createAccessToken(user.getId());
        String refreshToken = jwtManager.createRefreshToken(user.getId());

        return LoginResult.of(
                user,
                true,
                accessToken,
                jwtManager.getAccessTokenValidTimeDuration(),
                jwtManager.getAccessTokenValidTimeUnit(),
                refreshToken,
                jwtManager.getRefreshTokenValidTimeDuration(),
                jwtManager.getRefreshTokenValidTimeUnit()
        );
    }

    public void logout(Long userId) {
        User user = userRepository.getById(userId);
        user.validateHasKakaoAccessToken();
        kakaoOauthClient.logout(user.getKakaoAccessToken());
        user.updateKakaoAccessToken("");
    }

    public ReissueTokenResult reissueToken(Long userId) {
        User user = userRepository.getById(userId);

        String accessToken = jwtManager.createAccessToken(user.getId());

        return new ReissueTokenResult(
                accessToken,
                jwtManager.getAccessTokenValidTimeDuration(),
                jwtManager.getAccessTokenValidTimeUnit()
        );
    }
}
