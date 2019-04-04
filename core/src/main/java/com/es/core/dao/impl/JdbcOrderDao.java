package com.es.core.dao.impl;

import com.es.core.dao.OrderDao;
import com.es.core.dao.OrderItemDao;
import com.es.core.dao.mappers.OrderRowMapper;
import com.es.core.exception.ItemNotFoundException;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.Types;
import java.util.Collections;
import java.util.Map;

@Repository
public class JdbcOrderDao implements OrderDao {
    private static final String SQL_INSERT_ORDER = "INSERT INTO orders (secureId, subtotal, deliveryPrice, totalPrice, firstName, lastName, " +
            "deliveryAddress, contactPhoneNo, status) VALUES(:secureId, :subtotal, :deliveryPrice, :totalPrice, :firstName, :lastName, " +
            ":deliveryAddress, :contactPhoneNo, :status)";
    private static final String SQL_GET_ORDER = "SELECT * FROM orders WHERE id = :id";
    private static final String SQL_GET_ORDER_BY_SECURE_ID = "SELECT * FROM orders WHERE secureId = :secureId";
    private static final String SQL_UPDATE_ORDER_STATUS = "UPDATE orders SET status = :status WHERE id = :id";

    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Resource
    private OrderItemDao orderItemDao;

    @Resource
    private OrderRowMapper orderRowMapper;

    @Override
    public void save(Order order) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(order);
        parameterSource.registerSqlType("status", Types.VARCHAR);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(SQL_INSERT_ORDER, parameterSource, keyHolder);
        if (!keyHolder.getKeyList().isEmpty()) {
            order.setId(keyHolder.getKey().longValue());
        }

        order.getOrderItems().forEach(orderItem -> orderItemDao.save(orderItem));
    }

    @Override
    public Order get(String secureId) {
        Map<String, String> params = Collections.singletonMap("secureId", secureId);
        Order order = jdbcTemplate.query(SQL_GET_ORDER_BY_SECURE_ID, params,
                resultSet -> {
                    if (resultSet.next()) {
                        return orderRowMapper.mapRow(resultSet, 1);
                    } else {
                        throw new ItemNotFoundException();
                    }
                });
        order.setOrderItems(orderItemDao.getItemsForOrder(order));
        return order;
    }

    @Override
    public void updateOrderStatus(Long id, OrderStatus orderStatus) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", id);
        parameterSource.addValue("status", orderStatus);
        parameterSource.registerSqlType("status", Types.VARCHAR);

        jdbcTemplate.update(SQL_UPDATE_ORDER_STATUS, parameterSource);
    }
}
