package com.hopoong.core;

public interface CouponIssueRequestPort {
    void sendMessage(Long userId, Long couponEventId);
}
