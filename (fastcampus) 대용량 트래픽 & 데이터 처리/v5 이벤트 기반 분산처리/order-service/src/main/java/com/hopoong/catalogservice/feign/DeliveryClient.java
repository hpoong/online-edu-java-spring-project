package com.hopoong.catalogservice.feign;

import com.hopoong.catalogservice.model.ProcessDeliveryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "deliveryClient", url = "http://localhost:8080")
public interface DeliveryClient {

    /*
     * 사용자 첫번째 배송지 조회
     */
    @GetMapping("/delivery/users/{userId}/first-address")
    Map<String, Object> getUserAddress(@PathVariable Long userId);


    /*
     * 사용자 특정 배송지 조회
     */
    @PostMapping("/delivery/adress/{addressId}")
    Map<String, Object> getAddress(@PathVariable Long addressId);


    /*
     * 배송 등록
     */
    @PostMapping("/delivery/process-delivery")
    Map<String, Object> registerDelivery(@RequestBody ProcessDeliveryDto dto);


    /*
     * 배송 조회
     */
    @PostMapping("/delivery/deliveries/{deliveryId}")
    Map<String, Object> getDelivery(@PathVariable Long deliveryId);

}
