package com.hopoong.mysql;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponJpaRepository extends JpaRepository<CouponEntity, Long> {

    List<CouponEntity> findAllByUserId(Long userId);
}