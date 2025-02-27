package com.hopoong.paymentservice.repository;

import com.hopoong.paymentservice.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository extends JpaRepository<PaymentEntity, Long> {

}
