package com.hopoong.paymentservice.repository;

import com.hopoong.paymentservice.entity.PaymentMethodEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodJpaRepository extends JpaRepository<PaymentMethodEntity, Long> {

}
