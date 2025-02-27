package com.hopoong.couponcore.repository.mysql;

import com.hopoong.couponcore.model.CouponIssue;
import com.hopoong.couponcore.model.QCouponIssue;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;



@Repository
@RequiredArgsConstructor
public class CouponIssueRepository {

    private final JPAQueryFactory queryFactory;

    public CouponIssue findFirstCouponIssue(long couponId, long userId) {
        QCouponIssue qCouponIssue = QCouponIssue.couponIssue;

        return queryFactory.selectFrom(qCouponIssue)
                .where(qCouponIssue.couponId.eq(couponId))
                .where(qCouponIssue.userId.eq(userId))
                .fetchFirst();
    }
}

