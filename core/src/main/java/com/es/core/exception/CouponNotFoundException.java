package com.es.core.exception;

public class CouponNotFoundException extends ItemNotFoundException{
    public CouponNotFoundException(String couponCode) {
        super("coupon", couponCode);
    }
}
