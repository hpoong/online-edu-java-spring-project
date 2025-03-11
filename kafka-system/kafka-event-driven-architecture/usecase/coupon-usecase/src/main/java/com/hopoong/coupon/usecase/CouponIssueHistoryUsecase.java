package com.hopoong.coupon.usecase;

public interface CouponIssueHistoryUsecase {
    boolean isFirstRequestFromUser(Long couponEventId, Long userId); // 첫번째로 받은건지 체크
    boolean hasRemainingCoupon(Long couponEventId); // 발급 수량이 남아있는지 체크
}
