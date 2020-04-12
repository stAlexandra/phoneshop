package com.es.core.dao.impl;

import com.es.core.dao.UserLevelToDiscountDao;
import com.es.core.dao.mappers.DiscountRowMapper;
import com.es.core.model.discount.Discount;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static com.es.core.dao.DbContract.DiscountsTable.*;

@Repository
@Transactional
public class JdbcUserLevelToDiscountDao implements UserLevelToDiscountDao {
    private static final String SQL_GET_DISCOUNT_BY_USER_LEVEL = "SELECT * FROM discounts " +
            "INNER JOIN discounts2userLevel ON discounts.id = discounts2userLevel.discountId " +
            "WHERE discounts2userLevel.userLevel = :userLevel";

    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Resource
    private DiscountRowMapper discountRowMapper;

    @Override
    @Transactional(readOnly = true)
    public Discount getDiscountByUserLevel(Integer userLevel) {
        SqlParameterSource parameterSource = new MapSqlParameterSource("userLevel", userLevel);
        return jdbcTemplate.queryForObject(SQL_GET_DISCOUNT_BY_USER_LEVEL, parameterSource, discountRowMapper);
    }
}
