package jpa.study.post.presentation.contoller;

import jpa.study.post.application.dto.PostsResponse;
import jpa.study.post.application.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/posts")
    public ResponseEntity<PostsResponse> getPostList(@PageableDefault(size = 10) Pageable pageable) {
        PostsResponse response = postService.getPostList(pageable);
        return ResponseEntity.ok().body(response);
    }

//    @PostMapping("/post")
//    public ResponseEntity<Void> addPost() {
//        return null;
//    }
}
