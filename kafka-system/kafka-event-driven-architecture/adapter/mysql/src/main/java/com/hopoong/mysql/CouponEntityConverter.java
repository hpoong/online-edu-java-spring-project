package com.hopoong.mysql;


import com.hopoong.domain.Coupon;
import com.hopoong.domain.CouponEvent;
import com.hopoong.domain.ResolvedCoupon;

public class CouponEntityConverter {

    public static CouponEntity toCouponEntity(Coupon coupon) {
        return new CouponEntity(
                coupon.getId(),
                coupon.getUserId(),
                coupon.getCouponEventId(),
                coupon.getIssuedAt(),
                coupon.getUsedAt(),
                null
        );
    }

    public static Coupon toCouponModel(CouponEntity couponEntity) {
        return new Coupon(
                couponEntity.getId(),
                couponEntity.getUserId(),
                couponEntity.getCouponEventId(),
                couponEntity.getIssuedAt(),
                couponEntity.getUsedAt()
        );
    }

    public static CouponEvent toCouponEventModel(CouponEventEntity couponEventEntity) {
        return new CouponEvent(
                couponEventEntity.getId(),
                couponEventEntity.getDisplayName(),
                couponEventEntity.getExpiresAt(),
                couponEventEntity.getIssueLimit()
        );
    }

    public static ResolvedCoupon toResolvedCouponModel(CouponEntity couponEntity) {
        return new ResolvedCoupon(
                toCouponModel(couponEntity),
                toCouponEventModel(couponEntity.getCouponEvent())
        );
    }
}