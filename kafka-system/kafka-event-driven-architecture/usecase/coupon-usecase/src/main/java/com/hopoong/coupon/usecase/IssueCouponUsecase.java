package com.hopoong.coupon.usecase;

import com.hopoong.domain.Coupon;

public interface IssueCouponUsecase {

    Coupon save(Long couponEventId, Long userId);
}
