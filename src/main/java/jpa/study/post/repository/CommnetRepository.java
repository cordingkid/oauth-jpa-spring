package jpa.study.post.repository;

import jpa.study.post.application.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommnetRepository extends JpaRepository<Comment, Long> {
}
