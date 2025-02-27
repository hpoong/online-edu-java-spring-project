package com.hopoong.deliveryservice.repository;


import com.hopoong.deliveryservice.entity.DeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<DeliveryEntity, Long> {

}
