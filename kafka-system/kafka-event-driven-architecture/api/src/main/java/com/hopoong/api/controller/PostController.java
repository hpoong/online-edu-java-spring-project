package com.hopoong.api.controller;

import com.hopoong.api.model.PostCreateRequest;
import com.hopoong.api.model.PostDetailDto;
import com.hopoong.api.model.PostDto;
import com.hopoong.api.model.PostUpdateRequest;
import com.hopoong.domain.Post;
import com.hopoong.domain.ResolvedPost;
import com.hopoong.post.usecase.PostCreateUsecase;
import com.hopoong.post.usecase.PostDeleteUsecase;
import com.hopoong.post.usecase.PostReadUsecase;
import com.hopoong.post.usecase.PostUpdateUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostCreateUsecase postCreateUsecase;
    private final PostUpdateUsecase postUpdateUsecase;
    private final PostDeleteUsecase postDeleteUsecase;
    private final PostReadUsecase postReadUsecase;

    /*
     * 글 등록
     */
    @PostMapping
    ResponseEntity<PostDto> createPost(@RequestBody PostCreateRequest request) {
        Post post = postCreateUsecase.create(
            new PostCreateUsecase.Request(
                request.getUserId(),
                request.getTitle(),
                request.getContent(),
                request.getCategoryId()
            )
        );
        return ResponseEntity.ok().body(toDto(post));
    }

    /*
     * 글 수정
     */
    @PutMapping("/{postId}")
    ResponseEntity<PostDto> updatePost(
        @PathVariable("postId") Long id,
        @RequestBody PostUpdateRequest request
    ) {
        Post post = postUpdateUsecase.update(
            new PostUpdateUsecase.Request(
                id,
                request.getTitle(),
                request.getContent(),
                request.getCategoryId()
            )
        );
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(toDto(post));
    }

    /*
     * 글 삭제
     */
    @DeleteMapping("/{postId}")
    ResponseEntity<PostDto> deletePost(@PathVariable("postId") Long id) {
        Post post = postDeleteUsecase.delete(new PostDeleteUsecase.Request(id));
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(toDto(post));
    }

    /*
     * 글 상세
     */
    @GetMapping("/{postId}/detail")
    ResponseEntity<PostDetailDto> readPostDetail(@PathVariable("postId") Long id) {
        ResolvedPost resolvedPost = postReadUsecase.getById(id);
        if (resolvedPost == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(toDto(resolvedPost));
    }

    private PostDto toDto(Post post) {
        return new PostDto(
            post.getId(),
            post.getTitle(),
            post.getContent(),
            post.getUserId(),
            post.getCategoryId(),
            post.getCreatedAt(),
            post.getUpdatedAt(),
            post.getDeletedAt()
        );
    }

    private PostDetailDto toDto(ResolvedPost resolvedPost) {
        return new PostDetailDto(
            resolvedPost.getId(),
            resolvedPost.getTitle(),
            resolvedPost.getContent(),
            resolvedPost.getUserName(),
            resolvedPost.getCategoryName(),
            resolvedPost.getCreatedAt(),
            resolvedPost.isUpdated()
        );
    }
}
