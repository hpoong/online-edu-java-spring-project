package com.hopoong.deliveryservice.controller;

import com.hopoong.deliveryservice.entity.DeliveryEntity;
import com.hopoong.deliveryservice.entity.UserAddressEntity;
import com.hopoong.deliveryservice.model.ProcessDeliveryDto;
import com.hopoong.deliveryservice.model.RegisterAddressDto;
import com.hopoong.deliveryservice.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;


    // TODO : 해당 부분 필요 없음
//    /*
//     * 배송 등록
//     */
//    @PostMapping("/delivery/process-delivery")
//    public DeliveryEntity processDelivery(@RequestBody ProcessDeliveryDto dto) {
//        return deliveryService.processDelivery(dto.getOrderId(), dto.getProductName(), dto.getProductCount(), dto.getAddress());
//    }


    /*
     * 배송 조회
     */
    @PostMapping("/delivery/deliveries/{deliveryId}")
    public DeliveryEntity getDelivery(@PathVariable Long deliveryId) {
        return deliveryService.getDelivery(deliveryId);
    }


    /*
     * 사용자 배송지 등록
     */
    @PostMapping("/delivery/address")
    public UserAddressEntity addUserAddress(@RequestBody RegisterAddressDto dto) {
        return deliveryService.addUserAddress(dto.getUserId(), dto.getAddress(), dto.getAlias());
    }


    /*
     * 사용자 특정 배송지 조회
     */
    @PostMapping("/delivery/adress/{addressId}")
    public UserAddressEntity getAddress(@PathVariable Long addressId) {
        return deliveryService.getAddress(addressId);
    }


    /*
     * 사용자 첫번째 배송지 조회
     */
    @GetMapping("/delivery/users/{userId}/first-address")
    public UserAddressEntity getUserAddress(@PathVariable Long userId) {
        return deliveryService.getUserAddress(userId);
    }


}
