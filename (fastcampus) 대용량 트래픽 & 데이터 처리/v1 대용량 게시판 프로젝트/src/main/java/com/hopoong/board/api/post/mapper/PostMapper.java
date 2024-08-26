package com.hopoong.board.api.post.mapper;

import com.hopoong.board.api.post.model.PostsModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {

    void insertPostsBatch(List<PostsModel> list);

    List<PostsModel> findAllPostsByNameLike();
}
