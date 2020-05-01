package com.es.core.dao.impl;

import com.es.core.dao.CouponDao;
import com.es.core.dao.mappers.CouponRowMapper;
import com.es.core.model.discount.Coupon;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Repository
@Transactional
public class JdbcCouponDao implements CouponDao {
    private static final String SQL_GET_COUPON_BY_CODE = "SELECT * FROM discounts WHERE code != '' AND code = :code";

    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Resource
    private CouponRowMapper couponRowMapper;

    @Override
    @Transactional(readOnly = true)
    public Coupon getByCode(String code) {
        SqlParameterSource parameterSource = new MapSqlParameterSource("code", code);
        try {
            List<Coupon> couponList = jdbcTemplate.query(SQL_GET_COUPON_BY_CODE, parameterSource, new BeanPropertyRowMapper(Coupon.class));
            return CollectionUtils.isEmpty(couponList) ? null : couponList.get(0);
        } catch (DataAccessException exc) {
            // TODO: log
            return null;
        }
    }
}
