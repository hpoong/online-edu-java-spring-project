package com.hopoong.couponcore.service;

import com.hopoong.couponcore.repository.redis.RedisRepository;
import com.hopoong.couponcore.util.CouponRedisUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.hopoong.couponcore.util.CouponRedisUtils.getIssueRequestKey;

@Service
@RequiredArgsConstructor
public class CouponIssueRedisService {

    private final RedisRepository redisRepository;


    /*
     * 쿠폰 수량 검증
     */
    public boolean availableTotalIssueQuantity(Integer totalQuantity, long couponId) {
        if(totalQuantity == null) {
            return true;
        }
        
        // issue:request:couponId key에 저장되어 있는 size 값 조회
        // totalQuantity 값으로 체크
        String key = getIssueRequestKey(couponId);
        return totalQuantity > redisRepository.sCard(key);
    }



    /*
     * 쿠폰 중복 발급 검증
     */
    public boolean availableUserIssueQuantity(long couponId, long userId) {
        String key = getIssueRequestKey(couponId);
        return !redisRepository.sIsMember(key, String.valueOf(userId));
    }




}
