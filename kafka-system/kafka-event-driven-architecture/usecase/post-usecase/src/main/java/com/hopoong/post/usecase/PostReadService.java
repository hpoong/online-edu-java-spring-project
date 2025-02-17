package com.hopoong.post.usecase;

import com.hopoong.domain.ResolvedPost;
import com.hopoong.post.resolving.help.usecase.PostResolvingHelpUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostReadService implements PostReadUsecase {

    private final PostResolvingHelpUsecase postResolvingHelpUsecase;

    @Override
    public ResolvedPost getById(Long id) {
        return postResolvingHelpUsecase.resolvePostById(id);
    }
}
