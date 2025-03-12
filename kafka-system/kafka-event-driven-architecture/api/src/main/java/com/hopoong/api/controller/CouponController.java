package com.hopoong.api.controller;


import com.hopoong.api.model.CouponDto;
import com.hopoong.api.model.CouponIssueRequest;
import com.hopoong.coupon.usecase.CouponIssueHistoryUsecase;
import com.hopoong.coupon.usecase.ListUsableCouponsUsecase;
import com.hopoong.coupon.usecase.RequestCouponIssueUsecase;
import com.hopoong.domain.ResolvedCoupon;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/coupons")
public class CouponController {

    private final CouponIssueHistoryUsecase couponIssueHistoryUsecase;
    private final RequestCouponIssueUsecase requestCouponIssueUsecase;
    private final ListUsableCouponsUsecase listUsableCouponsUsecase;


    @PostMapping
    ResponseEntity<String> issue(
            @RequestBody CouponIssueRequest request
    ) {
        // 해당 부분은 사실 동시성이 충분한 포인트는 아니다 Lua Script 추천한다.
        if (!couponIssueHistoryUsecase.isFirstRequestFromUser(request.getCouponEventId(), request.getUserId())) {
            return ResponseEntity.status(HttpStatus.OK).body("Already tried to issue a coupon\n");
        }
        if (!couponIssueHistoryUsecase.hasRemainingCoupon(request.getCouponEventId())) {
            return ResponseEntity.status(HttpStatus.OK).body("Not enough available coupons\n");
        }
        requestCouponIssueUsecase.queue(request.getCouponEventId(), request.getUserId());
        return ResponseEntity.ok().body("Successfully Issued\n");
    }

    @GetMapping
    ResponseEntity<List<CouponDto>> listUsableCoupons(
            @RequestParam(name = "userId", defaultValue = "0", required = false) Long userId
    ) {
        List<ResolvedCoupon> resolvedCoupons = listUsableCouponsUsecase.listByUserId(userId);
        return ResponseEntity.ok().body(resolvedCoupons.stream().map(this::toDto).toList());
    }



    private CouponDto toDto(ResolvedCoupon resolvedCoupon) {
        return new CouponDto(
                resolvedCoupon.getCoupon().getId(),
                resolvedCoupon.getCouponEvent().getDisplayName(),
                resolvedCoupon.getCouponEvent().getExpiresAt()
        );
    }

}