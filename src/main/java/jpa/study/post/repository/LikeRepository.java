package jpa.study.post.repository;

import jpa.study.post.application.domain.Post;
import jpa.study.post.application.domain.Like;
import jpa.study.user.application.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByUserAndPost(User user, Post post);
}
