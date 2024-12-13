package com.hopoong.catalogservice.feign;

import com.hopoong.catalogservice.model.ProcessPaymentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "paymentClinet", url = "http://localhost:8080")
public interface PaymentClinet {

    /*
     * user 결제 수단 조회
     */
    @GetMapping("/payment/users/{userId}/first-method")
    Map<String, Object> getPaymentMethodUser(@PathVariable Long userId);

    /*
     * 결제
     */
    @PostMapping("/payment/process-payment")
    Map<String, Object> processPayment(@RequestBody ProcessPaymentDto dto);

    /*
     * 결제 내역 조회
     */
    @GetMapping("/payment/payments/{paymentId}")
    Map<String, Object> getPayment(@PathVariable Long paymentId);
}
