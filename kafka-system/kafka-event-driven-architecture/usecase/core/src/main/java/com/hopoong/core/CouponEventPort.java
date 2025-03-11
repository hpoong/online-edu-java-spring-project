package com.hopoong.core;

import com.hopoong.domain.CouponEvent;

public interface CouponEventPort {

    CouponEvent findById(Long id);
}
