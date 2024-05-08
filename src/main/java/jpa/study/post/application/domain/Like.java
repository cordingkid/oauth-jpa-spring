package jpa.study.post.application.domain;

import jakarta.persistence.*;
import jpa.study.common.BaseEntity;
import jpa.study.user.application.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.*;

@Entity
@Getter
@Table(name = "likes")
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Like extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Like(User user, Post post) {
        this.post = post;
        this.user = user;
    }

    public static Like of(User user, Post post) {
        Like like = Like.builder()
                .user(user)
                .post(post)
                .build();
        post.likes.add(like);
        return like;
    }
}
