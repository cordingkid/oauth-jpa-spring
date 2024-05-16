package jpa.study.post.application.dto;

import jpa.study.post.application.domain.Comment;
import jpa.study.post.application.domain.Like;
import jpa.study.post.application.domain.Post;
import lombok.Builder;

import java.util.List;

@Builder
public record PostResponse(
        Long postId,
        String content,
        int viewCount,
        Long userId,
        String nickname,
        List<LikeInfo> likes,
        List<CommentInfo> comments
) {

    public static PostResponse of(Post post) {
        return PostResponse.builder()
                .postId(post.getId())
                .content(post.getContent())
                .viewCount(post.getViewCount())
                .userId(post.getUser().getId())
                .nickname(post.getUser().getNickname())
                .likes(toLikeList(post.getLikes()))
                .comments(toCommentList(post.getComments()))
                .build();
    }

    public static List<LikeInfo> toLikeList(List<Like> likes) {
        return likes.stream()
                .map(LikeInfo::of)
                .toList();
    }

    public static List<CommentInfo> toCommentList(List<Comment> comments) {
        return comments.stream()
                .map(CommentInfo::of)
                .toList();
    }

    @Builder
    public record LikeInfo(
            Long likeId,
            Long postId,
            Long userId,
            String nickname
    ){

        public static LikeInfo of(Like like) {
            return LikeInfo.builder()
                    .likeId(like.getId())
                    .postId(like.getPost().getId())
                    .userId(like.getUser().getId())
                    .nickname(like.getUser().getNickname())
                    .build();
        }
    }

    @Builder
    public record CommentInfo(
            Long commentId,
            String content,
            Long postId,
            Long writerId,
            String writerNickname
    ){

        public static CommentInfo of(Comment comment) {
            return CommentInfo.builder()
                    .commentId(comment.getId())
                    .content(comment.getContent())
                    .postId(comment.getPost().getId())
                    .writerId(comment.getUser().getId())
                    .writerNickname(comment.getUser().getNickname())
                    .build();
        }
    }
}
