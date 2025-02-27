package com.hopoong.catalogservice.mysql.repository;

import com.hopoong.catalogservice.mysql.entity.SellerProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface SellerProductRepository extends JpaRepository<SellerProductEntity, Long> {

    List<SellerProductEntity> findBySellerId(Long sellerId);
}
