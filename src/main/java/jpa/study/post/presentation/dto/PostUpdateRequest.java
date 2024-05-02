package jpa.study.post.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostUpdateRequest {
    private Long postId;
    private String content;
}
