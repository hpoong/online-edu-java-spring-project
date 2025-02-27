package com.hopoong.deliveryservice.model;

import lombok.Data;

@Data
public class ProcessDeliveryDto {

    // TODO : 해당 부분 필요 없음
    private  Long orderId;

    private String productName;

    private Long productCount;

    private String address;
}
