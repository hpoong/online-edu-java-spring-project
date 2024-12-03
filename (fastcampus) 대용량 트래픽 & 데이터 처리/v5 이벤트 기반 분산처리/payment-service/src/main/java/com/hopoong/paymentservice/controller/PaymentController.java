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


    @PostMapping("/payment/methods")
    public PaymentMethodEntity registerUser(@RequestBody PaymentMethodDto dto) {
        return paymentService.registerPaymentMethod(dto.getUserId(), dto.getPaymentMethodType(), dto.getCreditCardNumber());
    }


    @PostMapping("/payment/process-payment")
    public PaymentEntity registerUser(@RequestBody ProcessPaymentDto dto) {
        return paymentService.processPayment(dto.getUserId(), dto.getOrderId(), dto.getAmountKRW(), dto.getPaymentMethodId());
    }


//    @GetMapping("/payment/users/{userId}/first-method")
//    public PaymentMethodEntity registerUser(@PathVariable Long userId) {
//
//    }
//
//    @GetMapping("/payment/methods")
//    public PaymentMethodEntity registerUser(@PathVariable Long paymentId) {
//
//    }




}
