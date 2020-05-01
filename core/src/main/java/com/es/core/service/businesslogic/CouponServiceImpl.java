package com.es.core.service.businesslogic;

import com.es.core.dao.CouponDao;
import com.es.core.model.discount.Coupon;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class CouponServiceImpl implements CouponService {
    @Resource
    CouponDao couponDao;

    @Override
    public Optional<Coupon> getCouponByCode(String code) {
        return Optional.ofNullable(couponDao.getByCode(code));
    }
}
