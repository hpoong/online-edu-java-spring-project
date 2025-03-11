package com.hopoong.coupon.usecase;

import com.hopoong.domain.ResolvedCoupon;

import java.util.List;

public interface ListUsableCouponsUsecase {

    List<ResolvedCoupon> listByUserId(Long userId);
}
