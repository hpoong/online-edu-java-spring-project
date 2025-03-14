package com.hopoong.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostCreateRequest {
    private String title;
    private Long userId;
    private String content;
    private Long categoryId;
}