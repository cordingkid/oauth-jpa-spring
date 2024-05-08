package jpa.study.post.repository.custom.impl;

import jpa.study.post.application.domain.Post;
import jpa.study.post.repository.PostRepository;
import jpa.study.user.application.domain.User;
import jpa.study.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CustomPostRepositoryImplTest {

    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;


    @BeforeEach
    void init() {
        User user1 = userInit(123L);
        userRepository.save(user1);

        User user2 = userInit(456L);
        userRepository.save(user2);

        for (int i = 0; i < 100; i++) {
            Post post = null;
            if (i % 2 == 0) {
                post = new Post("내용" + i, user1);
            } else {
                post = new Post("내용" + i, user2);
            }
            postRepository.save(post);
        }
    }

    @Test
    void 게시물_조회한다() {
        PageRequest request = PageRequest.of(1, 10);

        Slice<Post> result = postRepository.getPostList(request);

        List<Post> content = result.getContent();

        System.out.println("content = " + content.size());
    }

    private User userInit(Long oauthId) {
        return User.init(
                oauthId,
                "test@gmail.com",
                "1234"
        );
    }
}