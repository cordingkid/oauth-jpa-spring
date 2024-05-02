package jpa.study.user.application.domain;

import jakarta.persistence.*;
import jpa.study.common.BaseEntity;
import jpa.study.common.exception.CustomException;
import jpa.study.common.exception.ErrorCode;
import lombok.*;

import static jakarta.persistence.GenerationType.IDENTITY;
import static jpa.study.common.exception.ErrorCode.ALREADY_LOGOUT_EXCEPTION;
import static lombok.AccessLevel.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(name = "users")
@NoArgsConstructor(access = PROTECTED)
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private Long oauthId;

    @Column(nullable = false)
    private String oauthEmail;

    @Column(name = "nickname", unique = true, length = 10, nullable = false)
    private String nickname;
//    @Embedded
//    private Nickname nickname;

    @Column(nullable = false)
    private String kakaoAccessToken;

    @Column(nullable = false, length = 5)
    private boolean isDelete;

    public User(Long oauthId, String oauthEmail, String nickname, String kakaoAccessToken, boolean isDelete) {
        this.oauthId = oauthId;
        this.oauthEmail = oauthEmail;
        this.nickname = nickname;
        this.kakaoAccessToken = kakaoAccessToken;
        this.isDelete = isDelete;
    }

    public static User init(Long oauthId, String oauthEmail, String kakaoAccessToken) {
        return new User(
                oauthId,
                oauthEmail,
                String.valueOf(oauthId),
                kakaoAccessToken,
                false
        );
    }

    public void validateHasKakaoAccessToken() {
        if (kakaoAccessToken == null || kakaoAccessToken.isBlank()) {
            throw new CustomException(ALREADY_LOGOUT_EXCEPTION);
        }
    }

    public void updateKakaoAccessToken(String kakaoAccessToken) {
        this.kakaoAccessToken = kakaoAccessToken;
    }
}
