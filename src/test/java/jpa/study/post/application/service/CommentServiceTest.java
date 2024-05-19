package jpa.study.post.application.service;

import jpa.study.post.application.domain.Comment;
import jpa.study.post.presentation.dto.CommentWriteRequest;
import jpa.study.post.presentation.dto.PostWriteRequest;
import jpa.study.post.repository.CommentRepository;
import jpa.study.post.repository.PostRepository;
import jpa.study.user.application.domain.User;
import jpa.study.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentServiceTest {

    @Autowired
    CommentService commentService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostService postService;

    @Autowired
    CommentRepository commentRepository;

    @Test
    void 댓글을_작성한다() {
        User user = userInit();
        userRepository.save(user);

        Long userId = user.getId();

        PostWriteRequest request = new PostWriteRequest("게시글 작성입니다.");

        Long postId = postService.writePost(userId, request);

        CommentWriteRequest commentRequest = new CommentWriteRequest(userId, postId, "댓글 작성");

        Long commentId = commentService.writeComment(userId, postId, commentRequest);

        Comment comment = commentRepository.findById(commentId).get();

        System.out.println("comment.getContent() = " + comment.getContent());
        assertThat(comment).isNotNull();
    }

    private User userInit() {
        return User.init(
                1234L,
                "test@gmail.com",
                "1234"
        );
    }

}