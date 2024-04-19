package jpa.study.auth.application.dto;

import jpa.study.user.application.domain.User;

import java.util.concurrent.TimeUnit;

public record LoginResult(
        Long id,
        String nickname,
        boolean isFirst,
        String accessToken,
        int accessTokenValidTimeDuration,
        TimeUnit accessTokenValidTimeUnit
) {

    public static LoginResult of(
            User user,
            boolean isFirst,
            String accessToken,
            int accessTokenValidTimeDuration,
            TimeUnit accessTokenValidTimeUnit
    ){
        return new LoginResult(
                user.getId(),
                user.getNickname(),
                isFirst,
                accessToken,
                accessTokenValidTimeDuration,
                accessTokenValidTimeUnit
        );
    }
}
