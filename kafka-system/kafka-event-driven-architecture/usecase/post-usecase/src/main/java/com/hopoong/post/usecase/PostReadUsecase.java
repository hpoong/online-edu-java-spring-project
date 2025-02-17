package com.hopoong.post.usecase;


import com.hopoong.domain.ResolvedPost;

public interface PostReadUsecase {

    ResolvedPost getById(Long id);
}
