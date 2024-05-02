package jpa.study.post.application.service;

import jpa.study.common.exception.CustomException;
import jpa.study.post.application.domain.Post;
import jpa.study.post.presentation.dto.PostUpdateRequest;
import jpa.study.post.presentation.dto.PostWriteRequest;
import jpa.study.post.repository.PostRepository;
import jpa.study.user.application.domain.User;
import jpa.study.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public void writePost(Long userId, PostWriteRequest request) {
        User user = userRepository.getById(userId);

        Post post = new Post(request.getContent(), user);

        postRepository.save(post);
    }

    public void updatePost(Long userId, Long postId, PostUpdateRequest request) {
        User user = userRepository.getById(userId);
        Post foundPost = postRepository.getById(postId);

        foundPost.validateUpdateAuthority(user);
        foundPost.updatePost(request.getContent());
    }
}
