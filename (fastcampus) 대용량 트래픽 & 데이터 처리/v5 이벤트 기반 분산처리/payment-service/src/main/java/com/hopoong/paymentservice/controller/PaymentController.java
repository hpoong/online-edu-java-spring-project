package com.hopoong.paymentservice.controller;

import com.hopoong.paymentservice.entity.PaymentEntity;
import com.hopoong.paymentservice.entity.PaymentMethodEntity;
import com.hopoong.paymentservice.model.PaymentMethodDto;
import com.hopoong.paymentservice.model.ProcessPaymentDto;
import com.hopoong.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    /*
     * 결제 수단 등록
     */
    @PostMapping("/payment/methods")
    public PaymentMethodEntity registerUser(@RequestBody PaymentMethodDto dto) {
        return paymentService.registerPaymentMethod(dto.getUserId(), dto.getPaymentMethodType(), dto.getCreditCardNumber());
    }

    /*
     * 결제
     */
    @PostMapping("/payment/process-payment")
    public PaymentEntity registerUser(@RequestBody ProcessPaymentDto dto) {
        return paymentService.processPayment(dto.getUserId(), dto.getOrderId(), dto.getAmountKRW(), dto.getPaymentMethodId());
    }

    /*
     * user 결제 수단 조회
     */
    @GetMapping("/payment/users/{userId}/first-method")
    public PaymentMethodEntity getPaymentMethodUser(@PathVariable Long userId) {
        return paymentService.getPaymentMethodUser(userId);
    }

    /*
     * 결제 내역 조회
     */
    @GetMapping("/payment/payments/{paymentId}")
    public PaymentEntity getPayment(@PathVariable Long paymentId) {
        return paymentService.getPayment(paymentId);
    }
}
