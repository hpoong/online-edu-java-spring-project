package com.hopoong.couponapi;

import com.hopoong.couponapi.controller.dto.CouponIssueResponseDto;
import com.hopoong.couponcore.exception.CouponIssueException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CouponControllerAdvice {

    @ExceptionHandler(CouponIssueException.class)
    public CouponIssueResponseDto couponIssueExceptionHandler(CouponIssueException ex) {
        return new CouponIssueResponseDto(false, ex.getErrorCode().message);

    }

}
