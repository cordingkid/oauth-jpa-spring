package jpa.study.post.application.service;

import jpa.study.post.application.domain.Comment;
import jpa.study.post.application.domain.Post;
import jpa.study.post.presentation.dto.CommentUpdateRequest;
import jpa.study.post.presentation.dto.CommentWriteRequest;
import jpa.study.post.repository.CommentRepository;
import jpa.study.post.repository.PostRepository;
import jpa.study.post.repository.ReplyRepository;
import jpa.study.user.application.domain.User;
import jpa.study.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ReplyRepository replyRepository;

    public Long writeComment(Long userId, Long postId, CommentWriteRequest request) {
        User user = userRepository.getById(userId);
        Post post = postRepository.getById(postId);

        Comment comment = Comment.builder()
                .content(request.getContent())
                .user(user)
                .post(post)
                .build();

        commentRepository.save(comment);

        return comment.getId();
    }

    public Long updateComment(Long userId, Long postId, CommentUpdateRequest request) {
        User user = userRepository.getById(userId);
        Post post = postRepository.getById(postId);

        Comment found = commentRepository.getById(request.getCommentId());

        found.validateUpdateAuthority(user);

        found.updateComment(request.getContent());

        return found.getId();
    }
}
