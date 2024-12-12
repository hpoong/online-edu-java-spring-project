package com.hopoong.catalogservice.service;

import com.hopoong.catalogservice.feign.CatalogClient;
import com.hopoong.catalogservice.feign.DeliveryClient;
import com.hopoong.catalogservice.feign.PaymentClinet;
import com.hopoong.catalogservice.repository.ProductOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class OrderService {

    private ProductOrderRepository productOrderRepository;
    private final CatalogClient catalogClient;
    private final DeliveryClient deliveryClient;
    private final PaymentClinet paymentClinet;



}
