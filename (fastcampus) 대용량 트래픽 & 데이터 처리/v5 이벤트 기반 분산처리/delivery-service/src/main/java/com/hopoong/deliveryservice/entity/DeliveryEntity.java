package com.hopoong.deliveryservice.entity;

import com.hopoong.deliveryservice.entity.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "delivery", indexes = {@Index(name = "idx_orderId", columnList = "orderId")})
public class DeliveryEntity {

    // 배송 Table

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    private String productName;

    private Long productCount;

    private String address;

    private Long referenceCode;

    private DeliveryStatus deliveryStatus;


    public DeliveryEntity(Long orderId, String productName, Long productCount, String address, Long referenceCode, DeliveryStatus deliveryStatus) {
        this.orderId = orderId;
        this.productName = productName;
        this.productCount = productCount;
        this.address = address;
        this.referenceCode = referenceCode;
        this.deliveryStatus = deliveryStatus;
    }
}
