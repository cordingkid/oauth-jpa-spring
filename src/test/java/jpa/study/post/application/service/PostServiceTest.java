package jpa.study.post.application.service;

import jpa.study.post.application.domain.Post;
import jpa.study.post.presentation.dto.PostUpdateRequest;
import jpa.study.post.presentation.dto.PostWriteRequest;
import jpa.study.post.repository.PostRepository;
import jpa.study.user.application.domain.User;
import jpa.study.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class PostServiceTest {


    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Nested
    class 게시글 {

        @Test
        void 게시글을_작성한다() {
            User user = userInit();
            userRepository.save(user);

            Long userId = user.getId();

            PostWriteRequest request = new PostWriteRequest("게시글 작성입니다.");

            postService.writePost(userId, request);
        }

        @Test
        void 게시글을_수정한다() {
            User user = userInit();
            userRepository.save(user);

            Long userId = user.getId();

            PostWriteRequest request = new PostWriteRequest("게시글 작성입니다.");

            postService.writePost(userId, request);

            List<Post> postList = postRepository.findByUserId(userId);

            Post post = postList.get(0);

            PostUpdateRequest updateRequest = new PostUpdateRequest(post.getId(), "게시글 수정입니다.");

            postService.updatePost(userId, post.getId(), updateRequest);
        }
    }

    private User userInit() {
        return User.init(
                1234L,
                "test@gmail.com",
                "1234"
        );
    }
}