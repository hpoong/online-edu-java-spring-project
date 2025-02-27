package com.hopoong.couponcore.service;


import com.hopoong.couponcore.model.Coupon;
import com.hopoong.couponcore.repository.redis.dto.CouponRedisEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.framework.AopContext;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CouponCacheService {

    private final CouponIssueService couponIssueService;


    /*
     * redis cache
     */
    @Cacheable(cacheNames = "coupon", key = "#couponId")
    public CouponRedisEntity getCouponCache(long couponId) {
        Coupon coupon = couponIssueService.findCoupon(couponId);
        return new CouponRedisEntity(coupon);
    }


    /*
     * local cache 부터 확인 후 redis cache
     */
    @Cacheable(cacheNames = "coupons", key = "#couponId", cacheManager = "localCacheManager")
    public CouponRedisEntity getCouponLocalCache(long couponId) {
        return proxy().getCouponCache(couponId);
    }


    public CouponCacheService proxy() {
        return ((CouponCacheService) AopContext.currentProxy());
    }

    @CachePut(cacheNames = "coupons", key = "#couponId")
    public CouponRedisEntity putCouponCache(long couponId) {
        return getCouponCache(couponId);
    }


    @CachePut(cacheNames = "coupons", key = "#couponId", cacheManager = "localCacheManager")
    public CouponRedisEntity putCouponLocalCache(long couponId) {
        return getCouponCache(couponId);
    }



}
