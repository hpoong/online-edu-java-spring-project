package com.hopoong.kafkaproducer.model;

import lombok.Data;

import java.util.Map;

@Data
public class EffectOrNot { // 광고를 보고 구매한 유저

    private String adId;  // 광고 ID
    private String userId; // 사용자 ID
    private String orderId; // 주문 ID
    private Map<String, String> productInfo; // 제품정보
}
