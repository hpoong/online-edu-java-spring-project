package com.hopoong.post.resolving.help.usecase;

import com.hopoong.domain.ResolvedPost;

import java.util.List;

public interface PostResolvingHelpUsecase {

    ResolvedPost resolvePostById(Long postId);
    List<ResolvedPost> resolvePostsByIds(List<Long> postIds);
}
