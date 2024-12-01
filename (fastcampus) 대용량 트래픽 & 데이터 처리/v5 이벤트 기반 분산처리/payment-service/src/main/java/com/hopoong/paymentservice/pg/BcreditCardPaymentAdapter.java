package com.hopoong.paymentservice.pg;

import org.springframework.stereotype.Service;

@Service
public class BcreditCardPaymentAdapter implements CreditCardPaymentAdapter {

    @Override
    public Long processCreditCardPayment(Long amountKRW, String creditCardNumber) {
        return Math.round(Math.random() * 10);
    }
}
