package com.hopoong.catalogservice.model;

import lombok.Data;

@Data
public class FinishOrderDto {

    private Long orderId;
    private Long paymentMethodId;
    private Long addressId;
}
