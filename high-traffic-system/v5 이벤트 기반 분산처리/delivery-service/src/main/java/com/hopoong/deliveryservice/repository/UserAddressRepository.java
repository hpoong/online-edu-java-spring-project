package com.hopoong.deliveryservice.repository;

import com.hopoong.deliveryservice.entity.UserAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserAddressRepository extends JpaRepository<UserAddressEntity, Long> {
    List<UserAddressEntity> findByUserId(Long userId);
}
