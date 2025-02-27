package com.hopoong.catalogservice.mysql.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "seller_product")
@Entity
@NoArgsConstructor
@Data
public class SellerProductEntity {

    // 판매자 정보 테이블
    // pk : 제품 ID 값

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long sellerId;


    public SellerProductEntity(Long sellerId) {
        this.sellerId = sellerId;
    }
}
