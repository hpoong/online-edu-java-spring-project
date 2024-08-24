package com.hopoong.board.api.post.model;


import lombok.Builder;
import lombok.Data;

import java.util.Date;


@Data
public class PostsModel {

    private int id;
    private String name;
    private int isAdmin;
    private String contents;
    private Date createTime;
    private int views;
    private int categoryId;
    private int userId;
    private int fileId;
    private Date updateTime;


    @Builder
    public PostsModel(String name, String contents) {
        this.name = name;
        this.contents = contents;
    }

}
