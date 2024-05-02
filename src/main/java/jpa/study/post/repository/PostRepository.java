package jpa.study.post.repository;

import jpa.study.common.exception.CustomException;
import jpa.study.common.exception.ErrorCode;
import jpa.study.post.application.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static jpa.study.common.exception.ErrorCode.*;

public interface PostRepository extends JpaRepository<Post, Long> {


    default Post getById(Long postId){
        return findById(postId)
                .orElseThrow(() -> new CustomException(RESOURCE_NOT_FOUND));
    }

    @Query("""
            select p
            from Post p
            where p.user.id = :userId
            """)
    List<Post> findByUserId(@Param("userId") Long userId);
}
