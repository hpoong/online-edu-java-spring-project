package com.hopoong.kafka.adapter.couponissuerequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hopoong.common.CustomObjectMapper;
import com.hopoong.core.CouponIssueRequestPort;
import com.hopoong.kafka.adapter.common.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class CouponIssueRequestAdapter implements CouponIssueRequestPort {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final CustomObjectMapper objectMapper = new CustomObjectMapper();

    @Override
    public void sendMessage(Long userId, Long couponEventId) {
        CouponIssueRequestMessage message = CouponIssueRequestMessage.from(userId, couponEventId);
        try {
            kafkaTemplate.send(Topic.COUPON_ISSUE_REQUEST, message.getUserId().toString(), objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}