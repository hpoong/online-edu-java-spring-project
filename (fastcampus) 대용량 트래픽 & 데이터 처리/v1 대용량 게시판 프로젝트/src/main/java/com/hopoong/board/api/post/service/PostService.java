package com.hopoong.board.api.post.service;

import com.hopoong.board.api.post.mapper.PostMapper;
import com.hopoong.board.api.post.model.PostsModel;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostMapper postMapper;

    // 20만건
    @Transactional
    public void insertInitData() {

        List<PostsModel> list = new ArrayList<>();

        for(int i=0; i<200000; i++) {
            PostsModel postsModel = PostsModel.builder()
                    .name("name " + i).contents("contents " + i).build();
            list.add(postsModel);
        }

        postMapper.insertPostsBatch(list);
    }

    // 20만건 조회
    public List<PostsModel> noCachePostsSearch() {
        return postMapper.findAllPostsByNameLike();
    }

    // 20만건 조회
    @Cacheable(value = "getPostsCache", key = "'allPosts'")
    public List<PostsModel> cachePostsSearch() {
        return postMapper.findAllPostsByNameLike();
    }

}
