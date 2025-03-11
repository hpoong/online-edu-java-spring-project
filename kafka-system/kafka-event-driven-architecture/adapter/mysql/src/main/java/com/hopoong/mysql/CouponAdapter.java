package com.hopoong.mysql;

import com.hopoong.core.CouponPort;
import com.hopoong.domain.Coupon;
import com.hopoong.domain.ResolvedCoupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class CouponAdapter implements CouponPort {

    private final CouponJpaRepository couponJpaRepository;

    @Override
    public Coupon save(Coupon coupon) {
        CouponEntity couponEntity = couponJpaRepository.save(CouponEntityConverter.toCouponEntity(coupon));
        return CouponEntityConverter.toCouponModel(couponEntity);
    }

    @Override
    public List<ResolvedCoupon> listByUserId(Long userId) {
        List<CouponEntity> couponEntities = couponJpaRepository.findAllByUserId(userId);
        return couponEntities.stream().map(CouponEntityConverter::toResolvedCouponModel).toList();
    }
}