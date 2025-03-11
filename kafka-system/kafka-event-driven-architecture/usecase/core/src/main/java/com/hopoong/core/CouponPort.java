package com.hopoong.core;

import com.hopoong.domain.Coupon;
import com.hopoong.domain.ResolvedCoupon;

import java.util.List;

public interface CouponPort {

    Coupon save(Coupon coupon);
    List<ResolvedCoupon> listByUserId(Long userId);
}
