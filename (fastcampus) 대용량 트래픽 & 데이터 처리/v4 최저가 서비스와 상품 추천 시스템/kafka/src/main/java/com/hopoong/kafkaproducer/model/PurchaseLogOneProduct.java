package com.hopoong.kafkaproducer.model;

import lombok.Data;

@Data
public class PurchaseLogOneProduct {

    private String orderId;
    private String userId;
    private String productId;
    private String purchasedDt;
    private Long price;
}
