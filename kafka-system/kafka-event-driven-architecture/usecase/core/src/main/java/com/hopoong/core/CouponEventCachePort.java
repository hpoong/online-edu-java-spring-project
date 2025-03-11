package com.hopoong.core;

import com.hopoong.domain.CouponEvent;

public interface CouponEventCachePort {
    void set(CouponEvent couponEvent);
    CouponEvent get(Long couponEventId);
}
