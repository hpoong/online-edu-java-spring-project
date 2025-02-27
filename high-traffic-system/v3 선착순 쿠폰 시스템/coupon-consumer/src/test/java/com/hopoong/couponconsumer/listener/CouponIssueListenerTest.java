package com.hopoong.couponconsumer.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hopoong.couponconsumer.TestConfig;
import com.hopoong.couponcore.repository.redis.RedisRepository;
import com.hopoong.couponcore.service.CouponIssueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;


@Import(CouponIssueListener.class)
class CouponIssueListenerTest extends TestConfig {

    @Autowired
    CouponIssueListener sut;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    RedisRepository redisRepository;

    @MockBean
    CouponIssueService couponIssueService;

    @BeforeEach
    void clear() {
        Collection<String> redisKeys = redisTemplate.keys("*");
        redisTemplate.delete(redisKeys);
    }


    @Test
    @DisplayName("쿠폰 발급 큐에 처리 대상이 없다면 발급을 하지 않는다.")
    void issue_1() throws JsonProcessingException {
        // when
        sut.issue();

        // then
        Mockito.verify(couponIssueService, Mockito.never()).issue(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong());
    }



    @Test
    @DisplayName("쿠폰 발급 큐에 처리 대상이 있다면 발급 한다.")
    void issue_2() throws JsonProcessingException {

        // given
        long couponId = 1;
        long userId = 1;
        int totalQuantity = Integer.MAX_VALUE;
        redisRepository.issueRequest(couponId, userId, totalQuantity);

        // when
        sut.issue();

        // then
        Mockito.verify(couponIssueService, Mockito.times(1)).issue(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("쿠폰 발급 요청 순서에 맞게 처리된다.")
    void issue_3() throws JsonProcessingException {
        // given
        long couponId = 1;
        long userId1 = 1;
        long userId2 = 2;
        long userId3 = 3;
        int totalQuantity = Integer.MAX_VALUE;
        redisRepository.issueRequest(couponId, userId1, totalQuantity);
        redisRepository.issueRequest(couponId, userId2, totalQuantity);
        redisRepository.issueRequest(couponId, userId3, totalQuantity);

        // when
        sut.issue();

        // then
        InOrder inOrder = Mockito.inOrder(couponIssueService);
        inOrder.verify(couponIssueService, Mockito.times(1)).issue(couponId, userId1);
        inOrder.verify(couponIssueService, Mockito.times(1)).issue(couponId, userId2);
        inOrder.verify(couponIssueService, Mockito.times(1)).issue(couponId, userId3);
    }



}