package com.hopoong.kafkaproducer.model;


import lombok.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class PurchaseLog { // 구매한 이력

//    {"orderId":"orderId_001","userId":"userId_001","productInfo":[{"price":"10000","productId":"productId_001"},{"price":"20000","productId":"productId_002"}],"purchasedDt":"20241112"}

    private String orderId;     // 주문 ID
    private String userId;      // 사용자 ID
    private ArrayList<Map<String, String>> productInfo;  // 제품정보
    private String purchasedDt; // 구매 날짜
}
