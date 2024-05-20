package jpa.study.post.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentUpdateRequest {
    private Long commentId;
    private Long userId;
    private Long postId;
    private String content;
}
