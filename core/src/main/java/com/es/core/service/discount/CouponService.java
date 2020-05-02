package com.es.core.service.discount;

import com.es.core.model.discount.Coupon;

import java.util.Optional;

public interface CouponService {
    Optional<Coupon> getCouponByCode(String code);
}
