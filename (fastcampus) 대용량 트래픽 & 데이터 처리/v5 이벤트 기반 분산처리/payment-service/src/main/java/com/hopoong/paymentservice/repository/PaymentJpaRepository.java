package com.hopoong.paymentservice.repository;

import com.hopoong.paymentservice.entity.PaymentEntity;
import com.hopoong.paymentservice.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentJpaRepository extends JpaRepository<PaymentEntity, Long> {

}
