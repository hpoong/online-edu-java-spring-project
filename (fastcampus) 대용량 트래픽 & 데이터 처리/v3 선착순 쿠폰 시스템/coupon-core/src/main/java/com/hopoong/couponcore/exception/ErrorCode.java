package com.hopoong.couponcore.exception;

public enum ErrorCode {
    INVALID_COUPON_ISSUE_QUANTITY,  // 발급 수량
    INVALID_COUPON_ISSUE_DATE,      // 발급 기간
    COUPON_NOT_EXIST,
    DUPLICATED_COUPON_ISSUE,
}
