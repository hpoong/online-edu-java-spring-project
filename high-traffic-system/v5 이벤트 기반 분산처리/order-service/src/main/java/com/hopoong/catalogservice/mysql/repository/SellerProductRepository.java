package com.hopoong.catalogservice.mysql.repository;

import com.hopoong.catalogservice.mysql.entity.SellerProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellerProductRepository extends JpaRepository<SellerProductEntity, Long> {

    List<SellerProductEntity> findBySellerId(Long sellerId);
}
