package com.hopoong.kafkaproducer.model;


import lombok.Data;
import java.util.ArrayList;
import java.util.Map;

@Data
public class PurchaseLog { // 구매한 이력

    private String orderId;     // 주문 ID
    private String userId;      // 사용자 ID
    private ArrayList<String> productId;   // 제품 ID
//    private ArrayList<Map<String, String>> productInfo;  // 제품정보
    private String purchasedDt; // 구매 날짜
    private Long price;
}
