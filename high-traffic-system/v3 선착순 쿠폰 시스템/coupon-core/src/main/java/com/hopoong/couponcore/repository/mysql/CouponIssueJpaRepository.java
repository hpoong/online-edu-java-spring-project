package com.hopoong.couponcore.repository.mysql;

import com.hopoong.couponcore.model.CouponIssue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponIssueJpaRepository extends JpaRepository<CouponIssue, Long> {

}

