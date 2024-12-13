package com.hopoong.catalogservice.controller;

import com.hopoong.catalogservice.entity.ProductOrderEntity;
import com.hopoong.catalogservice.model.FinishOrderDto;
import com.hopoong.catalogservice.model.ProductOrderDetailDto;
import com.hopoong.catalogservice.model.StartOrderDto;
import com.hopoong.catalogservice.model.StartOrderResponseDto;
import com.hopoong.catalogservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order/start-order}")
    public StartOrderResponseDto startOrder(@RequestBody StartOrderDto dto) {
        return orderService.startOrder(dto.getUserId(), dto.getProductId(), dto.getCount());
    }

    @PostMapping("/order/finish-order")
    public ProductOrderEntity finishOrder(@RequestBody FinishOrderDto dto) {
        return orderService.finishOrder(dto.getOrderId(), dto.getPaymentMethodId(), dto.getAddressId());
    }

    @GetMapping("/order/users/{userId}/orders")
    public List<ProductOrderEntity> getUserOrders(@PathVariable Long userId) {
        return orderService.getUserOrders(userId);
    }

    @GetMapping("/order/orders/{orderId}")
    public ProductOrderDetailDto getOrder (@PathVariable Long orderId) {
        return orderService.getOrderDetail(orderId);
    }


}
