package com.hopoong.core;

public interface CouponIssueRequestHistoryPort {


    // 쿠폰 발급 여부
    boolean setHistoryIfNotExists(Long couponEventId, Long userId);

    // 해당 쿠폰이벤트 내에서, 발급 요청을 몇번째로 했는지 확인
    Long getRequestSequentialNumber(Long couponEventId);
}
