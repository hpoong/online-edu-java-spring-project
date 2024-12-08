package com.hopoong.catalogservice.model;

import lombok.Data;

import java.util.List;

@Data
public class ProductTagsDto {
    private Long productId;
    private List<String> tags;
}
