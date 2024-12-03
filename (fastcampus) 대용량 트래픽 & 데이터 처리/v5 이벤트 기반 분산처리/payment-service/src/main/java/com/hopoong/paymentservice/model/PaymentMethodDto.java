package com.hopoong.paymentservice.model;

import com.hopoong.paymentservice.entity.entity.PaymentMethodType;
import lombok.Data;

@Data
public class PaymentMethodDto {
    private Long userId;
    private PaymentMethodType paymentMethodType;
    private  String creditCardNumber;
}
