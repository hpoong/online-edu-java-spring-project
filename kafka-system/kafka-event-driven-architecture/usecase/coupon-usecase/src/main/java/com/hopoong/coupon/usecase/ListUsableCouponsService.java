package com.hopoong.coupon.usecase;

import com.hopoong.core.CouponPort;
import com.hopoong.domain.ResolvedCoupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class ListUsableCouponsService implements ListUsableCouponsUsecase {

    private final CouponPort couponPort;

    @Override
    public List<ResolvedCoupon> listByUserId(Long userId) {
        List<ResolvedCoupon> resolvedCoupons = couponPort.listByUserId(userId);
        return resolvedCoupons.stream().filter(ResolvedCoupon::canBeUsed).toList();
    }
}
