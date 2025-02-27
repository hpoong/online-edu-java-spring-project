package com.hopoong.paymentservice.model;

import com.hopoong.paymentservice.entity.entityEnum.PaymentMethodType;
import lombok.Data;

@Data
public class PaymentMethodDto {
    private Long userId;
    private PaymentMethodType paymentMethodType;
    private String creditCardNumber;
}
