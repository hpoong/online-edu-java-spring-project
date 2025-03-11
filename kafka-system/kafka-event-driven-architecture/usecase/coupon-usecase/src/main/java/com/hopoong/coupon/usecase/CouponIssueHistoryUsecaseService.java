package com.hopoong.coupon.usecase;


import com.hopoong.core.CouponEventCachePort;
import com.hopoong.core.CouponEventPort;
import com.hopoong.core.CouponIssueRequestHistoryPort;
import com.hopoong.domain.CouponEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CouponIssueHistoryUsecaseService implements CouponIssueHistoryUsecase {

    private final CouponIssueRequestHistoryPort couponIssueRequestHistoryPort;
    private final CouponEventPort couponEventPort;
    private final CouponEventCachePort couponEventCachePort;


    @Override
    public boolean isFirstRequestFromUser(Long couponEventId, Long userId) { // 첫번째 요청인지 확인
        return couponIssueRequestHistoryPort.setHistoryIfNotExists(couponEventId, userId); // redis를 통해서 데이터를 조회 mason ****
    }

    @Override
    public boolean hasRemainingCoupon(Long couponEventId) {
        CouponEvent couponEvent = couponEventPort.findById(couponEventId); // 캐시로 변경 mason ****
        return couponIssueRequestHistoryPort.getRequestSequentialNumber(couponEventId) <= couponEvent.getIssueLimit();
    }


    private CouponEvent getCouponEventById(Long couponEventId) {
        CouponEvent couponEventCache = couponEventCachePort.get(couponEventId);
        if (couponEventCache != null) {
            return couponEventCache;
        } else {
            CouponEvent couponEvent = couponEventPort.findById(couponEventId);
            if (couponEvent == null) {
                throw new RuntimeException("Coupon event does not exist.");
            }
            couponEventCachePort.set(couponEvent);
            return couponEvent;
        }
    }


}
