package com.hopoong.catalogservice.model;

import lombok.Data;

@Data
public class ProcessDeliveryDto {

    private  Long orderId;

    private String productName;

    private Long productCount;

    private String address;
}
