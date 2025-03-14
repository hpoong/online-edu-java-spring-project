package com.hopoong.coupon.usecase;


import com.hopoong.core.CouponIssueRequestPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RequestCouponIssueService implements RequestCouponIssueUsecase {

    private final CouponIssueRequestPort couponIssueRequestPort;

    @Override
    public void queue(Long couponEventId, Long userId) {
        couponIssueRequestPort.sendMessage(userId, couponEventId);
    }
}
