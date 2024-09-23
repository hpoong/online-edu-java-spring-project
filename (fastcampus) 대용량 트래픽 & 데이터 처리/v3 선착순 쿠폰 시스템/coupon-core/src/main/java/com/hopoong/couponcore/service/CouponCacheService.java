package com.hopoong.couponcore.service;


import com.hopoong.couponcore.model.Coupon;
import com.hopoong.couponcore.repository.redis.dto.CouponRedisEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.framework.AopContext;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CouponCacheService {

    private final CouponIssueService couponIssueService;


    @Cacheable(cacheNames = "coupon", key = "#couponId")
    public CouponRedisEntity getCouponCache(long couponId) {
        Coupon coupon = couponIssueService.findCoupon(couponId);
        return new CouponRedisEntity(coupon);
    }


    @Cacheable(cacheNames = "coupons", key = "#couponId", cacheManager = "localCacheManager")
    public CouponRedisEntity getCouponLocalCache(long couponId) {
        Coupon coupon = couponIssueService.findCoupon(couponId);
        return new CouponRedisEntity(coupon);
    }


    public CouponCacheService proxy() {
        return ((CouponCacheService) AopContext.currentProxy());
    }

}
