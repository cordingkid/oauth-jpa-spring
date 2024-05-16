package jpa.study.post.repository.custom.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jpa.study.common.util.PaginationUtils;
import jpa.study.post.application.domain.Post;
import jpa.study.post.application.domain.QComment;
import jpa.study.post.repository.custom.CustomPostRepository;
import jpa.study.user.application.domain.QUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Optional;

import static jpa.study.post.application.domain.QComment.comment;
import static jpa.study.post.application.domain.QLike.like;
import static jpa.study.post.application.domain.QPost.post;
import static jpa.study.user.application.domain.QUser.user;

@RequiredArgsConstructor
public class CustomPostRepositoryImpl implements CustomPostRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<Post> getPostList(Pageable pageable) {
        List<Post> fetch = queryFactory
                .selectFrom(post)
                .join(post.user, user).fetchJoin()
                .leftJoin(like).on(post.id.eq(like.post.id))
                .leftJoin(comment).on(post.id.eq(comment.post.id))
                .where(
                        user.isDelete.eq(false)
                )
                .limit(pageable.getPageSize() + 1)
                .orderBy(post.lastModifiedBy.desc())
                .fetch();
        return PaginationUtils.checkEndPage(pageable, fetch);
    }

    @Override
    public Optional<Post> findPost(Long postId) {
        Post fetchOne = queryFactory
                .selectFrom(post)
                .join(post.user, user).fetchJoin()
                .leftJoin(like).on(post.id.eq(like.post.id))
                .leftJoin(comment).on(post.id.eq(comment.post.id))
                .where(
                        post.id.eq(postId),
                        user.isDelete.eq(false)
                )
                .fetchOne();
        return Optional.ofNullable(fetchOne);
    }
}
