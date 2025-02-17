package com.hopoong.post.usecase;

import com.hopoong.domain.Post;
import org.springframework.stereotype.Service;

@Service
public class PostCreateService implements PostCreateUsecase {
    @Override
    public Post create(Request request) {
        return Post.generate(
            request.getUserId(),
            request.getTitle(),
            request.getContent(),
            request.getCategoryId()
        );
    }
}
