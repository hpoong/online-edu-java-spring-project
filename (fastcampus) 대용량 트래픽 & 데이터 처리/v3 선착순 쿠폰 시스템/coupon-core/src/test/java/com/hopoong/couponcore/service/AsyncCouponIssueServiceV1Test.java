package com.hopoong.couponcore.service;

import com.hopoong.couponcore.TestConfig;
import com.hopoong.couponcore.exception.CouponIssueException;
import com.hopoong.couponcore.exception.ErrorCode;
import com.hopoong.couponcore.model.CouponIssue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class AsyncCouponIssueServiceV1Test extends TestConfig {

    @Autowired
    AsyncCouponIssueServiceV1 sut;

    @Autowired
    RedisTemplate<String, String> redisTemplate;


    @BeforeEach
    void clear() {
        Collection<String> redisKeys = redisTemplate.keys("*");
        redisTemplate.delete(redisKeys);
    }


    @Test
    @DisplayName("쿠폰 발급 - 쿠폰이 존재하지 않는다면 예외 반환")
    void saveCouponIssue_1() {

        // given
        long couponId = 1;
        long userId = 1;

        CouponIssueException exception = Assertions.assertThrows(CouponIssueException.class, () -> {
            sut.issue(couponId, userId);
        });

        // when & then
        Assertions.assertEquals(exception.getErrorCode(), ErrorCode.COUPON_NOT_EXIST);
    }



}