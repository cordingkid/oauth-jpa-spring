package jpa.study.auth.infra.kakao.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.*;

@JsonNaming(SnakeCaseStrategy.class)
public record KakaoMember(
        Long id,
        KakaoAccount kakaoAccount
) {

    @JsonNaming(SnakeCaseStrategy.class)
    public record KakaoAccount(
            boolean emailNeedsAgreement,
            boolean isEmailValid,
            boolean isEmailVerified,
            String email
    ){

    }
}
