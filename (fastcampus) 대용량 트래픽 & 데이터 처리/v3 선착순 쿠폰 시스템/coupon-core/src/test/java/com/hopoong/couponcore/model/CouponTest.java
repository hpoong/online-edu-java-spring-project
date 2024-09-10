package com.hopoong.couponcore.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CouponTest {


    @Test
    @DisplayName("발급 수량이 남아 있으면 true 반환")
    void availableIssueQuantity_1() {
        Coupon coupon = Coupon.builder()
                .totalQuantity(100)
                .issuedQuantity(99)
                .build();

        boolean result = coupon.availableIssueQuantity();

        Assertions.assertTrue(result);
    }


    @Test
    @DisplayName("발급 수량이 소진되었다면 false 반환")
    void availableIssueQuantity_2() {
        Coupon coupon = Coupon.builder()
                .totalQuantity(100)
                .issuedQuantity(100)
                .build();

        boolean result = coupon.availableIssueQuantity();
        Assertions.assertFalse(result);
    }


    @Test
    @DisplayName("최대 발급 수량이 설정되지 않았다면 true 반환")
    void availableIssueQuantity_3() {
        Coupon coupon = Coupon.builder()
                .totalQuantity(null)
                .issuedQuantity(100)
                .build();

        boolean result = coupon.availableIssueQuantity();
        Assertions.assertTrue(result);
    }



    @Test
    @DisplayName("발급 기간이 시작되지 않았다면 false 반환")
    void availableIssueDate_1() {
        Coupon coupon = Coupon.builder()
                .dateIssueStart(LocalDateTime.now().plusDays(1))    // +1일
                .dateIssueEnd(LocalDateTime.now().plusDays(2))      // +2일
                .build();

        boolean result = coupon.availableIssueDate();
        Assertions.assertFalse(result);
    }


    @Test
    @DisplayName("발급 기간에 해당 되면 true 반환")
    void availableIssueDate_2() {
        Coupon coupon = Coupon.builder()
                .dateIssueStart(LocalDateTime.now().minusDays(1))   // -1일
                .dateIssueEnd(LocalDateTime.now().plusDays(2))      // +2일
                .build();

        System.out.println(coupon.getDateIssueStart());

        boolean result = coupon.availableIssueDate();
        Assertions.assertTrue(result);
    }


    @Test
    @DisplayName("발급 기간 종료 되면 false 반환")
    void availableIssueDate_3() {
        Coupon coupon = Coupon.builder()
                .dateIssueStart(LocalDateTime.now().minusDays(2)) // -2일
                .dateIssueEnd(LocalDateTime.now().minusDays(1)) // -1일
                .build();

        boolean result = coupon.availableIssueDate();
        Assertions.assertFalse(result);
    }


}