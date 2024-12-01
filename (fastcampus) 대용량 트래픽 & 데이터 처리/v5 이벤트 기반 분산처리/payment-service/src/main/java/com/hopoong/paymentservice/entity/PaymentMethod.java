package com.hopoong.paymentservice.entity;

import com.hopoong.paymentservice.entity.entity.PaymentMethodType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payment_method", indexes = {@Index(name = "idx_userId", columnList = "userId")})
@NoArgsConstructor
@Data
public class PaymentMethod {

    // 결제 수단 Table

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private PaymentMethodType paymentMethodType;

    private String creditCardNumber;


    public PaymentMethod(Long userId, PaymentMethodType paymentMethodType, String creditCardNumber) {
        this.userId = userId;
        this.paymentMethodType = paymentMethodType;
        this.creditCardNumber = creditCardNumber;
    }

}
