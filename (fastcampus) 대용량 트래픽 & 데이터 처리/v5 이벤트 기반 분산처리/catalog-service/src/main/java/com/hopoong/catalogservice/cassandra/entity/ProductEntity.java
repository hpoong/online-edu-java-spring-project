package com.hopoong.catalogservice.cassandra.entity;


import lombok.AllArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.List;

@Table(value = "product")
@AllArgsConstructor
public class ProductEntity {

    @PrimaryKey
    private Long id;

    @Column
    private Long sellerId;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private Long price;

    @Column
    private Long stockCount;

    @Column
    private List<String> tags;

}
