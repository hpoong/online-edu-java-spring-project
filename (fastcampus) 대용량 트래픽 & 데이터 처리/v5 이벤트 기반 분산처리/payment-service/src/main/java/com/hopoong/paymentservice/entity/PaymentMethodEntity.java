package com.hopoong.paymentservice.entity;

import com.hopoong.paymentservice.entity.entityEnum.PaymentMethodType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payment_method", indexes = {@Index(name = "idx_userId", columnList = "userId")})
@NoArgsConstructor
@Data
public class PaymentMethodEntity {

    // 결제 수단 Table

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private PaymentMethodType paymentMethodType;

    private String creditCardNumber;


    public PaymentMethodEntity(Long userId, PaymentMethodType paymentMethodType, String creditCardNumber) {
        this.userId = userId;
        this.paymentMethodType = paymentMethodType;
        this.creditCardNumber = creditCardNumber;
    }

}
