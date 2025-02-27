package com.hopoong.catalogservice.service;

import com.hopoong.catalogservice.entity.ProductOrderEntity;
import com.hopoong.catalogservice.entity.enums.OrderStatus;
import com.hopoong.catalogservice.feign.CatalogClient;
import com.hopoong.catalogservice.feign.DeliveryClient;
import com.hopoong.catalogservice.feign.PaymentClinet;
import com.hopoong.catalogservice.model.*;
import com.hopoong.catalogservice.repository.ProductOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderService {

    private ProductOrderRepository productOrderRepository;
    private final CatalogClient catalogClient;
    private final DeliveryClient deliveryClient;
    private final PaymentClinet paymentClinet;

    @Transactional
    public StartOrderResponseDto startOrder(Long userId, Long productId, Long count) {

        // 1. 상품 정보 조회
        var product = catalogClient.getProductById(productId);

        // 2. 결제 수단 정보 조회
        var paymentMehtod = paymentClinet.getPaymentMethodUser(userId);


        // 3. 배송지 정보 조회
        var address = deliveryClient.getUserAddress(userId);

        // 4. 주문 정보 생성
        var order = new ProductOrderEntity(userId, productId, count, OrderStatus.INITATED, null, null);

        productOrderRepository.save(order);

        StartOrderResponseDto startOrderResponseDto = new StartOrderResponseDto();
        startOrderResponseDto.setOrderId(order.getId());
        startOrderResponseDto.setPaymentMethod(paymentMehtod);
        startOrderResponseDto.setAddress(address);
        return startOrderResponseDto;
    }

    @Transactional
    public ProductOrderEntity finishOrder(Long orderId, Long paymentMethodId, Long addressId) {
        var order = productOrderRepository.findById(orderId).orElseThrow();

        // 상품 정보 조회
        var product = catalogClient.getProductById(order.getProductId());

        // 결제
        var processPaymentDto = new ProcessPaymentDto();
        processPaymentDto.setOrderId(order.getId());
        processPaymentDto.setUserId(order.getUserId());
        processPaymentDto.setAmountKRW(Long.parseLong(product.get("price").toString()) * order.getCount());
        processPaymentDto.setPaymentMethodId(paymentMethodId);

        var payment = paymentClinet.processPayment(processPaymentDto);

        // 배송요청
        var address = deliveryClient.getAddress(addressId);
        var processDeliveryDto = new ProcessDeliveryDto();
        processDeliveryDto.setOrderId(order.getId());
        processDeliveryDto.setProductName(product.get("name").toString());
        processDeliveryDto.setProductCount(order.getCount());
        processDeliveryDto.setAddress(address.get("address").toString());

        var delivery = deliveryClient.processDelivery(processDeliveryDto);

        // 상품 재고 감소
        var decreaseStockCountDto = new DecreaseStockCountDto();
        decreaseStockCountDto.setDecreaseCount(order.getCount());
        catalogClient.decreaseStockCount(order.getProductId(), decreaseStockCountDto);


        // 주문 정보 업데이트
        order.setPaymentId(Long.parseLong(payment.get("id").toString()));
        order.setDeliveryId(Long.parseLong(delivery.get("id").toString()));
        order.setOrderStatus(OrderStatus.DELIVERY_REQESTED);

        return productOrderRepository.save(order);
    }


    public List<ProductOrderEntity> getUserOrders(Long userId) {
        return productOrderRepository.findAllByUserId(userId);
    }


    public ProductOrderDetailDto getOrderDetail(Long orderId) {
        var order = productOrderRepository.findById(orderId).orElseThrow();

        var paymentRes = paymentClinet.getPayment(order.getPaymentId());
        var deliveryRes = deliveryClient.getDelivery(order.getDeliveryId());

        return new ProductOrderDetailDto(
                order.getId(),
                order.getUserId(),
                order.getProductId(),
                order.getPaymentId(),
                order.getDeliveryId(),
                order.getOrderStatus(),
                deliveryRes.get("status").toString(),
                paymentRes.get("paymentStatus").toString()
        );
    }








    










}
