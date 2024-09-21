package com.hopoong.couponapi.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.hopoong.couponapi.controller.dto.CouponIssueRequestDto;
import com.hopoong.couponcore.component.RedisLockExecutor;
import com.hopoong.couponcore.service.AsyncCouponIssueServiceV1;
import com.hopoong.couponcore.service.CouponIssueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponIssueRequestService {

    private final CouponIssueService couponIssueService;
    private final RedisLockExecutor redisLockExecutor;
    private final AsyncCouponIssueServiceV1 asyncCouponIssueServiceV1;

    /*
     * synchronized 활용 처리
     */
    public void issueRequestV1(CouponIssueRequestDto requestDto) {
        synchronized (this) {
            couponIssueService.issue(requestDto.couponId(), requestDto.userId());
        }
        log.info("쿠폰 발급 완료. couponId: %s, userId: %s".formatted(requestDto.couponId(), requestDto.userId()));
    }


    /*
     * Redis Lock 활용 처리
     */
    public void issueRequestV2(CouponIssueRequestDto requestDto) {

        redisLockExecutor.execute( "lock_" + requestDto.couponId(), 10000, 10000,  () -> {
            couponIssueService.issue(requestDto.couponId(), requestDto.userId());
        });

        log.info("쿠폰 발급 완료. couponId: %s, userId: %s".formatted(requestDto.couponId(), requestDto.userId()));
    }


    /*
     * DB Lock 활용 처리
     */
    public void issueRequestV3(CouponIssueRequestDto requestDto) {
        couponIssueService.issuev3(requestDto.couponId(), requestDto.userId());
    }


    public void asyncIssueRequestV1(CouponIssueRequestDto requestDto) throws JsonProcessingException {
        asyncCouponIssueServiceV1.issue(requestDto.couponId(), requestDto.userId());
    }

}
