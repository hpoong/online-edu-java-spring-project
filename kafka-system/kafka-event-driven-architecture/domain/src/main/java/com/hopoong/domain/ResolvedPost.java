package com.hopoong.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResolvedPost { // Post + 메타정보

    private Long id;
    private String title;
    private String content;
    private Long userId;
    private String userName;
    private Long categoryId;
    private String categoryName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean updated;

    public static ResolvedPost generate(
        Post post,
        String userName,
        String categoryName
    ) {
        return new ResolvedPost(
            post.getId(),
            post.getTitle(),
            post.getContent(),
            post.getUserId(),
            userName,
            post.getCategoryId(),
            categoryName,
            post.getCreatedAt(),
            post.getUpdatedAt(),
            !post.getCreatedAt().equals(post.getUpdatedAt())
        );
    }
}