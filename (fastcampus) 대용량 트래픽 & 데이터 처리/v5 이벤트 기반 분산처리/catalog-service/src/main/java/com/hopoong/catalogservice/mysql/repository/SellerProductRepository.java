package com.hopoong.catalogservice.mysql.repository;

import com.hopoong.catalogservice.mysql.entity.SellerProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface SellerProductRepository extends JpaRepository<SellerProductEntity, Long> {

}
