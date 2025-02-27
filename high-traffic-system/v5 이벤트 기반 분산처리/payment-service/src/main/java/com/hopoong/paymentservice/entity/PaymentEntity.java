package com.hopoong.paymentservice.entity;

import com.hopoong.paymentservice.entity.entityEnum.PaymentMethodType;
import com.hopoong.paymentservice.entity.entityEnum.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "payment", indexes = {@Index(name = "idx_userId", columnList = "userId")})
public class PaymentEntity {

    // 결제 Table

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Column(unique = true)
    private Long orderId;

    private Long amountKRW;

    private PaymentMethodType paymentMethodType;

    private String paymentData; // credit card 번호가 들어갈수있음.

    private PaymentStatus paymentStatus;

    @Column(unique = true)
    private Long referenceCode;


    public PaymentEntity(Long userId, Long orderId, Long amountKRW, PaymentMethodType paymentMethodType, String paymentData, PaymentStatus paymentStatus, Long referenceCode) {
        this.userId = userId;
        this.orderId = orderId;
        this.amountKRW = amountKRW;
        this.paymentMethodType = paymentMethodType;
        this.paymentData = paymentData;
        this.paymentStatus = paymentStatus;
        this.referenceCode = referenceCode;
    }
}
