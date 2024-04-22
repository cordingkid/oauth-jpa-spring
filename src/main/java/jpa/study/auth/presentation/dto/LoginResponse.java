package jpa.study.auth.presentation.dto;

import jpa.study.auth.application.dto.LoginResult;
import lombok.Builder;

@Builder
public record LoginResponse(
        Long id,
        String nickname,
        boolean isFirst,
        String accessToken,
        String refreshToken
) {

    public static LoginResponse of(LoginResult result) {
        return LoginResponse.builder()
                .id(result.id())
                .nickname(result.nickname())
                .isFirst(result.isFirst())
                .accessToken(result.accessToken())
                .refreshToken(result.refreshToken())
                .build();
    }
}
