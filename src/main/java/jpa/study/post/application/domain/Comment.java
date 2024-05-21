package jpa.study.post.application.domain;

import jakarta.persistence.*;
import jpa.study.common.BaseEntity;
import jpa.study.common.exception.CustomException;
import jpa.study.common.exception.ErrorCode;
import jpa.study.user.application.domain.User;
import lombok.*;

import static jakarta.persistence.FetchType.LAZY;
import static jpa.study.common.exception.ErrorCode.INVALID_ACCESS;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Comment extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public Comment(String content, Post post, User user) {
        this.content = content;
        this.post = post;
        this.user = user;
    }

    public void validateUpdateAuthority(User loginUser) {
        if (user.equals(loginUser)) {
            return;
        }
        throw new CustomException(INVALID_ACCESS);
    }

    public void updateComment(String content) {
        this.content = content;
    }
}
