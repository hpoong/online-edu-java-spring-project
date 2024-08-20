package com.hopoong.board.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonResponseCodeEnum {

    C01("", "C01");

    private final String type;
    private final String code;
}
