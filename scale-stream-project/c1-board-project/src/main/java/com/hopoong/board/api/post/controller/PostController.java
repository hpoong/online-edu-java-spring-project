package com.hopoong.board.api.post.controller;

import com.hopoong.board.api.post.service.PostService;
import com.hopoong.board.response.CommonResponseCodeEnum;
import com.hopoong.board.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/no-cache-search")
    public ResponseEntity<SuccessResponse> noCachePostsSearch() {
        return ResponseEntity.status(200)
                .body(new SuccessResponse(CommonResponseCodeEnum.C01, postService.noCachePostsSearch()));
    }


    @GetMapping("/cache-search")
    public ResponseEntity<SuccessResponse> cachePostsSearch() {
        return ResponseEntity.status(200)
                .body(new SuccessResponse(CommonResponseCodeEnum.C01, postService.cachePostsSearch()));
    }


    @GetMapping("/init-data")
    public ResponseEntity<SuccessResponse> initData() {
        postService.insertInitData();
        return ResponseEntity.status(200)
                .body(new SuccessResponse(CommonResponseCodeEnum.C01, "init-data"));
    }

}

