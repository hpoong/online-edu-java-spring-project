package com.hopoong.couponcore.repository.redis.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.hopoong.couponcore.exception.CouponIssueException;
import com.hopoong.couponcore.model.Coupon;
import com.hopoong.couponcore.model.CouponType;

import java.time.LocalDateTime;

import static com.hopoong.couponcore.exception.ErrorCode.INVALID_COUPON_ISSUE_DATE;
import static com.hopoong.couponcore.exception.ErrorCode.INVALID_COUPON_ISSUE_QUANTITY;

public record CouponRedisEntity(
        Long id,
        CouponType couponType,
        Integer totalQuantity,

        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        LocalDateTime dateIssueStart,

        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        LocalDateTime dateIssueEnd,

        boolean availableIssueQuantity
) {

    public CouponRedisEntity(Coupon coupon) {
        this(coupon.getId(), coupon.getCouponType(), coupon.getTotalQuantity(), coupon.getDateIssueStart(), coupon.getDateIssueEnd(), coupon.availableIssueQuantity());
    }

    /*
     * 쿠폰 유효성 검사 - 날짜
     */
    private boolean availableIssueDate() {
        LocalDateTime now = LocalDateTime.now();
        return dateIssueStart.isBefore(now) && dateIssueEnd.isAfter(now);
    }

    /*
     * 쿠폰 유효성 검사 - 발급일자, 수량 체크
     */
    public void checkIssuableCoupon() {

        if (!availableIssueQuantity) {
            throw new CouponIssueException(INVALID_COUPON_ISSUE_QUANTITY, "모든 발급 수량이 소진되었습니다. coupon_id : %s".formatted(id));
        }

        if(!this.availableIssueDate()) {
            throw new CouponIssueException(INVALID_COUPON_ISSUE_DATE, "발급 가능한 일자가 아닙니다. request : %s, issueStart: %s, issueEnd: %s".formatted(LocalDateTime.now(), dateIssueStart, dateIssueEnd));
        }
    }

}
