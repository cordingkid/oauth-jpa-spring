package jpa.study.auth.application.dto;

import java.util.concurrent.TimeUnit;

public record ReissueTokenResult(
        String accessToken,
        int accessTokenValidTimeDuration,
        TimeUnit accessTokenValidTimeUnit
) {
}
