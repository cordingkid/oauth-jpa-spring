package jpa.study.post.application.dto;

import jpa.study.post.application.domain.Post;
import lombok.Builder;

import java.util.List;

@Builder
public record PostsResponse(
        List<PostListInfo> posts,
        boolean hasNext
) {

    public static PostsResponse of(List<Post> postList, boolean hasNext) {
        return PostsResponse.builder()
                .posts(toList(postList))
                .hasNext(hasNext)
                .build();
    }

    public static List<PostListInfo> toList(List<Post> postList) {
        return postList.stream()
                .map(PostListInfo::of)
                .toList();
    }

    @Builder
    public record PostListInfo(
            Long postId,
            String content,
            Long userId,
            String nickname,
            int likeCount
    ){

        public static PostListInfo of(Post post) {
            return PostListInfo.builder()
                    .postId(post.getId())
                    .content(post.getContent())
                    .userId(post.getUser().getId())
                    .nickname(post.getUser().getNickname())
                    .likeCount(post.getLikes().size())
                    .build();
        }
    }
}
