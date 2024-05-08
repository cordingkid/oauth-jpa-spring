package jpa.study.post.application.service;

import jpa.study.post.application.domain.Like;
import jpa.study.post.application.domain.Post;
import jpa.study.post.application.dto.PostResponse;
import jpa.study.post.presentation.dto.PostUpdateRequest;
import jpa.study.post.presentation.dto.PostWriteRequest;
import jpa.study.post.repository.PostLikeRepository;
import jpa.study.post.repository.PostRepository;
import jpa.study.user.application.domain.User;
import jpa.study.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostLikeRepository likeRepository;

    @Transactional(readOnly = true)
    public PostResponse getPostList(Pageable pageable) {
        Slice<Post> result = postRepository.getPostList(pageable);
        return PostResponse.of(result.getContent(), result.hasNext());
    }

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

    public void removePost(Long postId) {
        Post foundPost = postRepository.getById(postId);
        postRepository.delete(foundPost);
    }

    public void likePost(Long userId, Long postId) {
        User foundUser = userRepository.getById(userId);
        Post foundPost = postRepository.getById(postId);

        Optional<Like> foundLike = likeRepository.findByUserAndPost(foundUser, foundPost);

        if (foundLike.isEmpty()) {
            Like like = Like.of(foundUser, foundPost);
            likeRepository.save(like);
        } else {
            likeRepository.delete(foundLike.get());
        }
    }
}
