package jpa.study.post.repository;

import jpa.study.common.exception.CustomException;
import jpa.study.post.application.domain.Post;
import jpa.study.post.application.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import static jpa.study.common.exception.ErrorCode.RESOURCE_NOT_FOUND;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    default Reply getById(Long replyId){
        return findById(replyId)
                .orElseThrow(() -> new CustomException(RESOURCE_NOT_FOUND));
    }
}
