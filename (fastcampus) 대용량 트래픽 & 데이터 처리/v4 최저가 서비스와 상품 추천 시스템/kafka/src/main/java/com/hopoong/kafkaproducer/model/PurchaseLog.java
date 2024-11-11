package com.hopoong.kafkaproducer.model;


import lombok.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class PurchaseLog { // 구매한 이력

//    {"orderId":"orderId_002","userId":"userId_002","productId":["productId_003","productId_004"],"purchasedDt":"20241108","price":50000}

    private String orderId;     // 주문 ID
    private String userId;      // 사용자 ID
    private List<String> productId;   // 제품 ID
//    private ArrayList<Map<String, String>> productInfo;  // 제품정보
    private String purchasedDt; // 구매 날짜
    private Long price;
}
