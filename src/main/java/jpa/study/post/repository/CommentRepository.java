package jpa.study.post.repository;

import jpa.study.common.exception.CustomException;
import jpa.study.post.application.domain.Comment;
import jpa.study.post.application.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import static jpa.study.common.exception.ErrorCode.RESOURCE_NOT_FOUND;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    default Comment getById(Long commentId){
        return findById(commentId)
                .orElseThrow(() -> new CustomException(RESOURCE_NOT_FOUND));
    }
}
