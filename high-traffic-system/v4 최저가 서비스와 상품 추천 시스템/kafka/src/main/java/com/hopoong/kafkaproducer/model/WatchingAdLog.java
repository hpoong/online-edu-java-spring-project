package com.hopoong.kafkaproducer.model;

import lombok.Data;


@Data
public class WatchingAdLog { // 광고 본 이력

// {"userId":"userId_001","productId":"productId_001","adId":"adId_001","adType":"cook","watchingTime":"20","watchingDt":"20241108"}

    private String userId;          // 사용자 ID
    private String productId;       // 제품 ID
    private String adId;            // 광고 ID
    private String adType;          // 광고 타입
    private String watchingTime;    // 머문시간
    private String watchingDt;      // 광고를 본 날짜
}
