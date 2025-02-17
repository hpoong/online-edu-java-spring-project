package com.hopoong.api.controller;

import com.hopoong.api.model.PostInListDto;
import com.hopoong.domain.ResolvedPost;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/list")
public class PostListController {

    /*
     * 구독 목록
     */
    @GetMapping("/inbox/{userId}")
    ResponseEntity<List<PostInListDto>> listSubscribingPosts(@PathVariable("userId") Long userId) {
        return ResponseEntity.internalServerError().build();
    }

    /*
     * 글 검색
     */
    @GetMapping("/search")
    ResponseEntity<List<PostInListDto>> searchPosts(@RequestParam("query") String query) {
        return ResponseEntity.internalServerError().build();
    }

    private PostInListDto toDto(ResolvedPost resolvedPost) {
        return new PostInListDto(
            resolvedPost.getId(),
            resolvedPost.getTitle(),
            resolvedPost.getUserName(),
            resolvedPost.getCreatedAt()
        );
    }
}
