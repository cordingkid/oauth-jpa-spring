package jpa.study.post.application.domain;

import jakarta.persistence.*;
import jpa.study.common.BaseEntity;
import jpa.study.common.exception.CustomException;
import jpa.study.user.application.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static jpa.study.common.exception.ErrorCode.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Post extends BaseEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post")
    List<Like> likes = new ArrayList<>();

    @Column(nullable = false)
    @ColumnDefault(value = "0")
    private int viewCount;

    public Post(String content, User user) {
        this.content = content;
        this.user = user;
    }

    public void validateUpdateAuthority(User loginUser) {
        if (user.equals(loginUser)) {
            return;
        }
        throw new CustomException(INVALID_ACCESS);
    }

    public void updatePost(String content) {
        this.content = content;
    }

    public void increasedViews(){
        viewCount++;
    }
}
