package jpa.study.post.application.dto;

import jpa.study.post.application.domain.Post;
import lombok.Builder;

import java.util.List;

@Builder
public record PostResponse(
        List<Post> posts,
        boolean hasNext
) {

    public static PostResponse of(List<Post> postList, boolean hasNext) {
        return PostResponse.builder()
                .posts(postList)
                .hasNext(hasNext)
                .build();
    }
}
