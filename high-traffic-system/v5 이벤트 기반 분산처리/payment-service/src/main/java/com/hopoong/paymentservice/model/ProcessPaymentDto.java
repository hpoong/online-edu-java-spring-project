package com.hopoong.paymentservice.model;

import lombok.Data;

// TODO : 해당 부분도 필요 없음.
@Data
public class ProcessPaymentDto {
    private Long userId;
    private Long orderId;
    private Long amountKRW;
    private Long paymentMethodId;
}
