package com.hopoong.coupon.usecase;

public interface RequestCouponIssueUsecase {

    void queue(Long couponEventId, Long userId);
}
