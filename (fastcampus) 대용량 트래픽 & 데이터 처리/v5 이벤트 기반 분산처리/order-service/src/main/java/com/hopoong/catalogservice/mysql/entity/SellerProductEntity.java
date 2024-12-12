package com.hopoong.catalogservice.mysql.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "seller_product")
@Entity
@NoArgsConstructor
@Data
public class SellerProductEntity {

    //

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long sellerId;


    public SellerProductEntity(Long sellerId) {
        this.sellerId = sellerId;
    }
}
