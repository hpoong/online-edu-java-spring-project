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

    }

    @PostMapping("/order/finish-order")
    public ProductOrderEntity finishOrder(@RequestBody FinishOrderDto dto) {

    }

    @GetMapping("/order/users/{userId}/orders")
    public List<ProductOrderEntity> getUserOrders(@PathVariable Long userId) {

    }

    @GetMapping("/order/orders/{orderId}")
    public ProductOrderDetailDto getOrder (@PathVariable Long orderId) {

    }


}
