package com.hopoong.catalogservice.model;

import lombok.Data;

import java.util.Map;

@Data
public class StartOrderResponseDto {

    private Long orderId;

    private Map<String, Object> paymentMethod;

    private Map<String, Object> address;
}
