package com.hopoong.couponconsumer.listener;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hopoong.couponcore.repository.redis.RedisRepository;
import com.hopoong.couponcore.repository.redis.dto.CouponIssueRequest;
import com.hopoong.couponcore.service.CouponIssueService;
import com.hopoong.couponcore.util.CouponRedisUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class CouponIssueListener {


    private final RedisRepository redisRepository;
    private final CouponIssueService couponIssueService;
    private final String issueRequestQueueKey = CouponRedisUtils.getIssueRequestQueueKey();
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Scheduled(fixedDelay = 1000L)
    public void issue() throws JsonProcessingException {
        log.info("listen...");
        while (existCouponIssueTarget()) {
            CouponIssueRequest target = getIssueTarget();
            log.info("발급 시작 target: " + target);
            couponIssueService.issue(target.couponId(), target.userId());
            log.info("발급 완료 target: " + target);
            removeIssuedTarget();
        }
    }

    /*
     * 쿠폰 발급 관리 Queue left pop
     */
    private void removeIssuedTarget() {
        redisRepository.lPop(issueRequestQueueKey);
    }

    /*
     * 쿠폰 발급 관리 Queue size 조회
     */
    private boolean existCouponIssueTarget() {
        return redisRepository.lSize(issueRequestQueueKey) > 0;
    }

    /*
     * 쿠폰 발급 관리 Queue 0번째 index 조회
     */
    private CouponIssueRequest getIssueTarget() throws JsonProcessingException {
        return objectMapper.readValue(redisRepository.lIndex(issueRequestQueueKey, 0), CouponIssueRequest.class);
    }


}
