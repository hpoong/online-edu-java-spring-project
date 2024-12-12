package com.hopoong.catalogservice.model;

import lombok.Data;

@Data
public class StartOrderDto {

    private Long userId;

    private Long productId;

    private Long count;
}
