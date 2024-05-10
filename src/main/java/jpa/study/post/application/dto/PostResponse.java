package jpa.study.post.application.dto;

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
        List<LikeInfo> likeInfoList
) {

    public static PostResponse of(Post post) {
        return PostResponse.builder()
                .postId(post.getId())
                .content(post.getContent())
                .viewCount(post.getViewCount())
                .userId(post.getUser().getId())
                .nickname(post.getUser().getNickname())
                .likeInfoList(toList(post.getLikes()))
                .build();
    }

    public static List<LikeInfo> toList(List<Like> likes) {
        return likes.stream()
                .map(LikeInfo::of)
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
}
