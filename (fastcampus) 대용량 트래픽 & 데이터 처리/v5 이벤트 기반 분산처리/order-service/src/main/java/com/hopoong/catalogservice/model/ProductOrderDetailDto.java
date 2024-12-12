package com.hopoong.catalogservice.model;

import com.hopoong.catalogservice.entity.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductOrderDetailDto {
    private Long id;

    private Long userId;

    private Long productId;

    private Long count;

    private OrderStatus orderStatus;

    private Long paymentId;

    private Long deliveryId;

    private String paymentStatus;

    private String deliveryStatus;
}
