package com.hopoong.deliveryservice.model;

import lombok.Data;

@Data
public class RegisterAddressDto {

    private Long userId;

    private String address;

    private String alias;
}
