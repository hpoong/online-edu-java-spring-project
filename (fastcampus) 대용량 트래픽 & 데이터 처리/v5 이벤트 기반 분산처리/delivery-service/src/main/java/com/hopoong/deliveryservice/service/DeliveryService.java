package com.hopoong.deliveryservice.service;

import com.hopoong.deliveryservice.entity.DeliveryEntity;
import com.hopoong.deliveryservice.entity.UserAddressEntity;
import com.hopoong.deliveryservice.entity.enums.DeliveryStatus;
import com.hopoong.deliveryservice.repository.DeliveryRepository;
import com.hopoong.deliveryservice.repository.UserAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final UserAddressRepository userAddressRepository;



    public UserAddressEntity addUserAddress(Long userId, String address, String alias) {
        var userAddress = new UserAddressEntity(userId, address, alias);
        return userAddressRepository.save(userAddress);
    }


    public DeliveryEntity processDelivery(Long orderId, String productName, Long productCount, String address) {
        var delivery = new DeliveryEntity(orderId, productName, productCount, address, 11L, DeliveryStatus.REQUSTED);
        return deliveryRepository.save(delivery);
    }


    public UserAddressEntity getAddress(Long addressId) {
        return userAddressRepository.findById(addressId).orElseThrow();
    }


    public UserAddressEntity getUserAddress(Long userId) {
        return userAddressRepository.findByUserId(userId).stream().findFirst().orElseThrow();
    }


    public DeliveryEntity getDelivery(Long deliveryId) {
        return deliveryRepository.findById(deliveryId).orElseThrow();
    }


}
