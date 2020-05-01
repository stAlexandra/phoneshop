package com.es.core.dao.mappers;

import com.es.core.model.discount.Coupon;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CouponRowMapper implements RowMapper<Coupon> {
    private final DiscountRowMapper discountRowMapper;

    public CouponRowMapper(DiscountRowMapper discountRowMapper) {
        this.discountRowMapper = discountRowMapper;
    }

    @Override
    public Coupon mapRow(ResultSet resultSet, int i) throws SQLException {
        Coupon coupon = (Coupon) discountRowMapper.mapRow(resultSet, i);
        coupon.setCode(resultSet.getString("code"));
        return coupon;
    }
}
