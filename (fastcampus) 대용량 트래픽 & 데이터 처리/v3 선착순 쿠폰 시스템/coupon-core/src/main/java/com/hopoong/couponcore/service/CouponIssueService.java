package com.hopoong.couponcore.service;

import com.hopoong.couponcore.exception.CouponIssueException;
import com.hopoong.couponcore.exception.ErrorCode;
import com.hopoong.couponcore.model.Coupon;
import com.hopoong.couponcore.model.CouponIssue;
import com.hopoong.couponcore.repository.mysql.CouponIssueJpaRepository;
import com.hopoong.couponcore.repository.mysql.CouponIssueRepository;
import com.hopoong.couponcore.repository.mysql.CouponJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponIssueService {

    private final CouponIssueJpaRepository couponIssueJpaRepository;
    private final CouponIssueRepository couponIssueRepository;
    private final CouponJpaRepository couponJpaRepository;


    /*
     * 쿠폰 발급
     * Lock 적용 x
     */
    @Transactional
    public void issue(long couponId, long userId) {
        Coupon coupon = this.findCoupon(couponId); // 쿠폰 확인
        coupon.issue(); // 검증 및 쿠폰 발급
        saveCouponIssue(couponId, userId);// 쿠폰 발급이력 저장
    }


    /*
     * 쿠폰 발급
     * DB Lock 적용
     */
    @Transactional
    public void issuev3(long couponId, long userId) {
        Coupon coupon = this.findCouponWithLock(couponId); // 쿠폰 확인
        coupon.issue(); // 검증 및 쿠폰 발급
        saveCouponIssue(couponId, userId);// 쿠폰 발급이력 저장
    }


    /*
     * 쿠폰 조회
     */
    @Transactional(readOnly = true)
    public Coupon findCoupon(long couponId) {
        return couponJpaRepository.findById(couponId).orElseThrow(() -> {
            throw new CouponIssueException(ErrorCode.COUPON_NOT_EXIST, "쿠폰 정책이 존재하지 않습니다. %s".formatted(couponId));
        });
    }


    /*
     * 쿠폰 조회
     * DB Lock
     */
    @Transactional
    public Coupon findCouponWithLock(long couponId) {
        return couponJpaRepository.findCouponWithLock(couponId).orElseThrow(() -> {
            throw new CouponIssueException(ErrorCode.COUPON_NOT_EXIST, "쿠폰 정책이 존재하지 않습니다. %s".formatted(couponId));
        });
    }

    /*
     * 쿠폰 발급 이력 저장
     */
    @Transactional
    public CouponIssue saveCouponIssue(long couponId, long userId) {
        checkAlreadyIssuance(couponId, userId);
        CouponIssue issue = CouponIssue.builder()
                .couponId(couponId)
                .userId(userId)
                .build();

        return couponIssueJpaRepository.save(issue);
    }

    /*
     * 쿠폰 발급 중복 검사
     */
    private void checkAlreadyIssuance(long couponId, long userId) {
        CouponIssue firstCouponIssue = couponIssueRepository.findFirstCouponIssue(couponId, userId);
        if(firstCouponIssue != null) {
            throw new CouponIssueException(ErrorCode.DUPLICATED_COUPON_ISSUE, "이미 발급된 쿠폰입니다. user_id: %d, coupon_id: %d".formatted(userId, couponId));
        }
    }


}
