package com.hopoong.couponapi.service;


import com.hopoong.couponapi.controller.dto.CouponIssueRequestDto;
import com.hopoong.couponcore.component.RedisLockExecutor;
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



}
