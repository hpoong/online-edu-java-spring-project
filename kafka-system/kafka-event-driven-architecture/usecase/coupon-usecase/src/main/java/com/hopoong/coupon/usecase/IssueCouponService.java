package com.hopoong.coupon.usecase;

import com.hopoong.core.CouponPort;
import com.hopoong.domain.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class IssueCouponService implements IssueCouponUsecase {

    private final CouponPort couponPort;

    @Override
    public Coupon save(Long couponEventId, Long userId) {
        Coupon coupon = Coupon.generate(userId, couponEventId);
        return couponPort.save(coupon);
    }


}
