package jpa.study.user.application.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jpa.study.common.BaseEntity;
import lombok.*;

import static lombok.AccessLevel.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(name = "users")
@NoArgsConstructor(access = PROTECTED)
public class User extends BaseEntity {

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

    public static User init(Long oauthId, String oauthEmail, String kakaoAccessToken) {
        return new User(
                oauthId,
                oauthEmail,
                String.valueOf(oauthId),
                kakaoAccessToken,
                false
        );
    }
}
