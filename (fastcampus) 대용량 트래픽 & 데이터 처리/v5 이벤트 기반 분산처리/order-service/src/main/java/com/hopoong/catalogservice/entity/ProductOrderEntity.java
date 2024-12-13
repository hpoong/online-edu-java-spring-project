package com.hopoong.catalogservice.entity;

import com.hopoong.catalogservice.entity.enums.OrderStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity(name = "product_order")
public class ProductOrderEntity {

    // 주문 상태 Table

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long productId;

    private Long count;

    private OrderStatus orderStatus;

    private Long paymentId;

    private Long deliveryId;

    public ProductOrderEntity(Long userId, Long productId, Long count, OrderStatus orderStatus, Long paymentId, Long deliveryId) {
        this.userId = userId;
        this.productId = productId;
        this.count = count;
        this.orderStatus = orderStatus;
        this.paymentId = paymentId;
        this.deliveryId = deliveryId;
    }
}
