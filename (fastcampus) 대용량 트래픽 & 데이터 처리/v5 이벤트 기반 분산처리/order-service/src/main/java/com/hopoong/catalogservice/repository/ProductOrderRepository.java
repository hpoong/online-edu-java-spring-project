package com.hopoong.catalogservice.repository;

import com.hopoong.catalogservice.entity.ProductOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductOrderRepository extends JpaRepository<ProductOrderEntity, Long> {

    List<ProductOrderEntity> findAllByUserId(Long userId);

}
