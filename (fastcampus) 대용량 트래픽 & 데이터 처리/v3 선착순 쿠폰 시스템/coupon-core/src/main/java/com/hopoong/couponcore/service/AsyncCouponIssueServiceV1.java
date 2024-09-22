package com.hopoong.couponcore.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hopoong.couponcore.component.RedisLockExecutor;
import com.hopoong.couponcore.repository.redis.RedisRepository;
import com.hopoong.couponcore.repository.redis.dto.CouponIssueRequest;
import com.hopoong.couponcore.repository.redis.dto.CouponRedisEntity;
import com.hopoong.couponcore.util.CouponRedisUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AsyncCouponIssueServiceV1 {

    private final RedisRepository redisRepository;
    private final CouponIssueRedisService couponIssueRedisService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RedisLockExecutor redisLockExecutor;
    private final CouponCacheService couponCacheService;



    public void issue(long couponId, long userId) {
        CouponRedisEntity coupon = couponCacheService.getCouponCache(couponId);

        // date 검증
        coupon.checkIssuableCoupon();

        // redis lock 처리
        redisLockExecutor.execute("lock_%s".formatted(couponId), 3000, 3000, () -> {

            // 수량 & 중복 발급 검증
            couponIssueRedisService.checkCouponIssueQuantity(coupon, userId);

            // 쿠폰 발급처리
            issueRequest(couponId, userId);
        });

    }


    private void issueRequest(long couponId, long userId) {
        try {
            CouponIssueRequest issueRequest = new CouponIssueRequest(couponId, userId);
            String value = objectMapper.writeValueAsString(issueRequest);

            // 쿠폰 발급 수량 & 쿠폰 중복 관리 Queue - set
            redisRepository.sAdd(CouponRedisUtils.getIssueRequestKey(couponId), String.valueOf(userId));

            // 쿠폰 발급 관리 Queue - list
            redisRepository.rPush(CouponRedisUtils.getIssueRequestQueueKey(), value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }





}
