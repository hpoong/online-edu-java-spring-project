package com.hopoong.catalogservice.model;

import lombok.Data;

import java.util.List;

@Data
public class RegisterProductDto {

    private Long sellerId;

    private String name;

    private String description;

    private Long price;

    protected Long stockCount;

    private List<String> tags;
}
