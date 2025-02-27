package com.hopoong.couponcore.util;

public class CouponRedisUtils {


    /*
     * 발급 수량 관리 Set
     */
    public static String getIssueRequestKey(long couponId) {
        return "issue:request:couponId=%s".formatted(couponId);
    }


    /*
     * 쿠폰 발급 관리 Queue
     */
    public static String getIssueRequestQueueKey() {
        return "issue:request";
    }


}
