package jpa.study.post.repository;

import jpa.study.post.application.domain.Post;
import jpa.study.post.application.domain.PostLike;
import jpa.study.user.application.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    Boolean existByUserAndPost(User user, Post post);
}