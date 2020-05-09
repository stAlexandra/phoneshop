package com.es.core.dao.impl;

import com.es.core.dao.UserDiscountsDao;
import com.es.core.dao.UserLevelDiscountDao;
import com.es.core.dao.mappers.DiscountRowMapper;
import com.es.core.exception.DuplicateDiscountException;
import com.es.core.model.discount.Discount;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Repository
@Transactional
public class JdbcDiscountDao implements UserLevelDiscountDao, UserDiscountsDao {
    private static final String SQL_GET_DISCOUNT_BY_USER_LEVEL = "SELECT * FROM discounts " +
            "INNER JOIN discounts2userLevel ON discounts.id = discounts2userLevel.discountId " +
            "WHERE discounts2userLevel.userLevel = :userLevel";
    private static final String SQL_GET_DISCOUNTS_BY_USER_NAME = "SELECT * FROM discounts " +
            "INNER JOIN discounts2users ON discounts.id = discounts2users.discountId " +
            "WHERE discounts2users.username = :username";
    private static final String SQL_INSERT_USER_DISCOUNT = "INSERT INTO discounts2users (username, discountId) VALUES(:username, :discountId)";

    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Resource
    private DiscountRowMapper discountRowMapper;

    @Override
    @Transactional(readOnly = true)
    public Discount getByUserLevel(Integer userLevel) {
        SqlParameterSource parameterSource = new MapSqlParameterSource("userLevel", userLevel);
        List<Discount> discountList = jdbcTemplate.query(SQL_GET_DISCOUNT_BY_USER_LEVEL, parameterSource, discountRowMapper);
        return CollectionUtils.isEmpty(discountList) ? null : discountList.get(0);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Discount> getByUserName(String username) {
        return jdbcTemplate.query(SQL_GET_DISCOUNTS_BY_USER_NAME,
                Collections.singletonMap("username", username), discountRowMapper);
    }

//    @Override
//    public void save(String username, Collection<Discount> discounts) throws DuplicateDiscountException { // TODO: check if needed
//        SqlParameterSource[] batchParams = discounts.stream()
//                .filter(discount -> discount != null && discount.getId() != null) // only save discounts that already exist
//                .map(discount -> {
//                    MapSqlParameterSource params = new MapSqlParameterSource();
//                    params.addValue("username", username);
//                    params.addValue("discountId", discount.getId());
//                    return params;
//                }).toArray(SqlParameterSource[]::new);
//        try {
//            jdbcTemplate.batchUpdate(SQL_INSERT_USER_DISCOUNT, batchParams);
//        } catch (DataAccessException exc) {
//            throw new DuplicateDiscountException(exc);
//        }
//    }

    @Override
    public void save(String username, @NotNull Discount discount) throws DuplicateDiscountException {
        if (discount.getId() == null) return;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username", username);
        params.addValue("discountId", discount.getId());
        try {
            jdbcTemplate.update(SQL_INSERT_USER_DISCOUNT, params);
        } catch (DataAccessException exc) {
            throw new DuplicateDiscountException(exc);
        }
    }
}
