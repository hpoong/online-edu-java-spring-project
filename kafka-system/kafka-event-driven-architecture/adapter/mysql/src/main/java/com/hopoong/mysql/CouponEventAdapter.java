package com.hopoong.mysql;


import com.hopoong.core.CouponEventPort;
import com.hopoong.domain.CouponEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponEventAdapter implements CouponEventPort {

    private final CouponEventJpaRepository couponEventJpaRepository;

    @Override
    public CouponEvent findById(Long id) {
        CouponEventEntity couponEventEntity = couponEventJpaRepository.findById(id).orElse(null);
        if (couponEventEntity == null) {
            return null;
        }
        return toModel(couponEventEntity);
    }

    private CouponEvent toModel(CouponEventEntity couponEventEntity) {
        return new CouponEvent(
                couponEventEntity.getId(),
                couponEventEntity.getDisplayName(),
                couponEventEntity.getExpiresAt(),
                couponEventEntity.getIssueLimit()
        );
    }
}




