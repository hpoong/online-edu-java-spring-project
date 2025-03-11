package com.hopoong.coupon.usecase;

import com.hopoong.domain.Post;
import lombok.Data;

public interface PostCreateUsecase {

    Post create(Request request);

    @Data
    class Request {
        private final Long userId;
        private final String title;
        private final String content;
        private final Long categoryId;
    }
}
