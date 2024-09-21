package com.hopoong.couponcore.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hopoong.couponcore.component.RedisLockExecutor;
import com.hopoong.couponcore.exception.CouponIssueException;
import com.hopoong.couponcore.exception.ErrorCode;
import com.hopoong.couponcore.model.Coupon;
import com.hopoong.couponcore.repository.redis.RedisRepository;
import com.hopoong.couponcore.repository.redis.dto.CouponIssueRequest;
import com.hopoong.couponcore.util.CouponRedisUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.hopoong.couponcore.exception.ErrorCode.*;

@RequiredArgsConstructor
@Service
public class AsyncCouponIssueServiceV1 {

    private final RedisRepository redisRepository;
    private final CouponIssueRedisService couponIssueRedisService;
    private final CouponIssueService couponIssueService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RedisLockExecutor redisLockExecutor;



    public void issue(long couponId, long userId) {
        Coupon coupon = couponIssueService.findCoupon(couponId);

        // date 검증
        if(!coupon.availableIssueDate()) {
            throw new CouponIssueException(INVALID_COUPON_ISSUE_DATE, "발급 가능한 일자가 아닙니다. couponId: %s, issueStart:%s, issueEnd:%s".formatted(couponId, coupon.getDateIssueStart(), coupon.getDateIssueEnd()));
        }

        // redis lock 처리
        redisLockExecutor.execute("lock%_s".formatted(couponId), 3000, 3000, () -> {
            // 쿠폰 수량 검증
            if(couponIssueRedisService.availableTotalIssueQuantity(coupon.getTotalQuantity(), couponId)) {
                throw new CouponIssueException(INVALID_COUPON_ISSUE_QUANTITY, "발급 가능한 수량을 초과합니다. couponId: %s, userId: %s".formatted(couponId, userId));
            }

            // 쿠폰 중복 발급 검증
            if(couponIssueRedisService.availableUserIssueQuantity(couponId, userId)) {
                throw new CouponIssueException(DUPLICATED_COUPON_ISSUE, "이미 발급 요청이 처리됐습니다. couponId: %s, userId: %s".formatted(couponId, userId));
            }

            // 쿠폰 발급처리
            issueRequest(couponId, userId);
        });

    }


    private void issueRequest(long couponId, long userId) {
        try {
            CouponIssueRequest issueRequest = new CouponIssueRequest(couponId, userId);
            String value = objectMapper.writeValueAsString(issueRequest);

            // 쿠폰 발급 수량 & 쿠폰 중복 관리 Queue
            redisRepository.sAdd(CouponRedisUtils.getIssueRequestKey(couponId), String.valueOf(userId));
            // 쿠폰 발급 관리 Queue
            redisRepository.rPush(CouponRedisUtils.getIssueRequestQueueKey(), value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }





}
