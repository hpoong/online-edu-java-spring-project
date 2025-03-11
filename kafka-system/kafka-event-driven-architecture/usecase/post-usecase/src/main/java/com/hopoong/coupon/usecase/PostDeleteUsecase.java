package com.hopoong.coupon.usecase;

import com.hopoong.domain.Post;
import lombok.Data;

public interface PostDeleteUsecase {

    Post delete(Request request);

    @Data
    class Request {
        private final Long postId;
    }
}
