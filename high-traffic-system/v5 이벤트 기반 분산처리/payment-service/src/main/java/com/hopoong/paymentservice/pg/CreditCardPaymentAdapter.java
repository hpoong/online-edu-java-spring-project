package com.hopoong.paymentservice.pg;

public interface CreditCardPaymentAdapter {

    Long processCreditCardPayment(Long amountKRW, String creditCardNumber);
}
