package com.hopoong.catalogservice.model;

import lombok.Data;

@Data
public class ProcessPaymentDto {
    private Long userId;
    private Long orderId;
    private Long amountKRW;
    private Long paymentMethodId;
}
