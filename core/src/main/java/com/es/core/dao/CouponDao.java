package com.es.core.dao;

import com.es.core.model.discount.Coupon;

public interface CouponDao {
    Coupon getByCode(String code);
}
