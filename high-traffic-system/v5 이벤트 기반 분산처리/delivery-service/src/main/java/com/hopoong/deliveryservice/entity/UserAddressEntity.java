package com.hopoong.deliveryservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "user_address", indexes = {@Index(name = "idx_userId", columnList = "userId")})
public class UserAddressEntity {

    // 사용자 배송지 Table

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String address;

    private String alias;


    public UserAddressEntity(Long userId, String address, String alias) {
        this.userId = userId;
        this.address = address;
        this.alias = alias;
    }
}
