package com.hopoong.paymentservice.service;

import com.hopoong.paymentservice.entity.PaymentEntity;
import com.hopoong.paymentservice.entity.PaymentMethodEntity;
import com.hopoong.paymentservice.entity.entityEnum.PaymentMethodType;
import com.hopoong.paymentservice.entity.entityEnum.PaymentStatus;
import com.hopoong.paymentservice.pg.CreditCardPaymentAdapter;
import com.hopoong.paymentservice.repository.PaymentJpaRepository;
import com.hopoong.paymentservice.repository.PaymentMethodJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
public class PaymentService {

    private final PaymentMethodJpaRepository paymentMethodJpaRepository;
    private final PaymentJpaRepository paymentJpaRepository;
    private final CreditCardPaymentAdapter creditCardPaymentAdapter;

    public PaymentService(
            PaymentMethodJpaRepository paymentMethodJpaRepository,
            PaymentJpaRepository paymentJpaRepository,
            @Qualifier("acreditCardPaymentAdapter") CreditCardPaymentAdapter creditCardPaymentAdapter) {
        this.paymentMethodJpaRepository = paymentMethodJpaRepository;
        this.paymentJpaRepository = paymentJpaRepository;
        this.creditCardPaymentAdapter = creditCardPaymentAdapter;
    }

    /*
     * 결제 수단 등록
     */
    public PaymentMethodEntity registerPaymentMethod(Long userId, PaymentMethodType paymentMethodType, String creditCardNumber){
        var paymentMethod = new PaymentMethodEntity(userId, paymentMethodType, creditCardNumber);
        return paymentMethodJpaRepository.save(paymentMethod);
    }

    /*
     * 결제
     */
    public PaymentEntity processPayment(
            Long userId,
            Long orderId,
            Long amountKRW,
            Long paymentMethodId
    ) {
        PaymentMethodEntity paymentMethodEntity = paymentMethodJpaRepository.findById(paymentMethodId)
                .orElseThrow(() -> new IllegalArgumentException("paymentMethodId not found"));

        if(!paymentMethodEntity.getPaymentMethodType().equals(PaymentMethodType.CREDIT_CARD)) {
            throw new IllegalArgumentException("신용카드만 지원합니다.");
        }

        // 실제 결제 진행 지불완료
        Long refCode = creditCardPaymentAdapter.processCreditCardPayment(amountKRW, paymentMethodEntity.getCreditCardNumber());
        var payment = new PaymentEntity(
                userId,
                orderId,
                amountKRW,
                paymentMethodEntity.getPaymentMethodType(),
                paymentMethodEntity.getCreditCardNumber(),
                PaymentStatus.COMPLETED,
                refCode
        );

        return paymentJpaRepository.save(payment);
    }


    /*
     * user 결제 수단 조회
     */
    public PaymentMethodEntity getPaymentMethodUser(Long userId) {
        return paymentMethodJpaRepository.findById(userId).stream().findFirst()
                .orElseThrow(() -> new IllegalArgumentException("userId not found"));
    }


    /*
     * 결제 내역 조회
     */
    public PaymentEntity getPayment(Long paymentId) {
        return paymentJpaRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("paymentId not found"));
    }


}
