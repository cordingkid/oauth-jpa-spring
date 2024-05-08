package jpa.study.post.repository.custom;

import jpa.study.post.application.domain.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface CustomPostRepository {

    Slice<Post> getPostList(Pageable pageable);
}