package com.es.core.dao.impl;

import com.es.core.dao.OrderItemDao;
import com.es.core.dao.mappers.OrderItemRowMapper;
import com.es.core.dao.mappers.OrderRowMapper;
import com.es.core.dao.mappers.PhoneRowMapper;
import com.es.core.exception.ItemNotFoundException;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcOrderItemDao implements OrderItemDao {
    private static final String SQL_GET_ORDER_ITEM = "SELECT * FROM orderItems oI INNER JOIN phones ph ON oI.phoneId = ph.id " +
            "INNER JOIN orders o ON oI.orderId = o.id WHERE oI.id = :id";
    private static final String SQL_INSERT_ORDER_ITEM = "INSERT INTO orderItems (phoneId, orderId, quantity) VALUES (:phoneId, :orderId, :quantity)";
    private static final String SQL_GET_ALL_ORDER_ITEMS = "SELECT * FROM orderItems oI INNER JOIN phones ph ON oI.phoneId = ph.id " +
            "INNER JOIN orders o ON oI.orderId = o.id WHERE oI.orderId = :orderId";
    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Resource
    private OrderItemRowMapper orderItemRowMapper;

    @Resource
    private OrderRowMapper orderRowMapper;

    @Resource
    private PhoneRowMapper phoneRowMapper;

    @Override
    public OrderItem get(Long id) {
        Map<String, Long> params = Collections.singletonMap("id", id);
        return jdbcTemplate.query(SQL_GET_ORDER_ITEM, params, resultSet -> {
            if (resultSet.next()) {
                OrderItem orderItem = orderItemRowMapper.mapRow(resultSet, 1);
                orderItem.setOrder(orderRowMapper.mapRow(resultSet, 1));
                orderItem.setPhone(phoneRowMapper.mapRow(resultSet, 1));
                return orderItem;
            } else {
                throw new ItemNotFoundException(id);
            }
        });
    }

    @Override
    public void save(OrderItem orderItem) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("phoneId", orderItem.getPhone().getId());
        params.addValue("orderId", orderItem.getOrder().getId());
        params.addValue("quantity", orderItem.getQuantity());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(SQL_INSERT_ORDER_ITEM, params, keyHolder);
        orderItem.setId(keyHolder.getKey().longValue());
    }

    @Override
    public List<OrderItem> getItemsForOrder(Long orderId) {
        Map<String, Long> params = Collections.singletonMap("orderId", orderId);
        List<OrderItem> orderItems = new ArrayList<>();
        jdbcTemplate.query(SQL_GET_ALL_ORDER_ITEMS, params, resultSet -> {
            do {
                OrderItem orderItem = orderItemRowMapper.mapRow(resultSet, 1);
                orderItem.setPhone(phoneRowMapper.mapRow(resultSet, 1));
                orderItem.setOrder(orderRowMapper.mapRow(resultSet, 1));
                orderItems.add(orderItem);
            } while (resultSet.next());
        });
        return orderItems;
    }

    @Override
    public List<OrderItem> getItemsForOrder(Order order) {
        Map<String, Long> params = Collections.singletonMap("orderId", order.getId());
        List<OrderItem> orderItems = new ArrayList<>();
        jdbcTemplate.query(SQL_GET_ALL_ORDER_ITEMS, params, resultSet -> {
            do {
                OrderItem orderItem = orderItemRowMapper.mapRow(resultSet, 1);
                orderItem.setPhone(phoneRowMapper.mapRow(resultSet, 1));
                orderItem.setOrder(order);
                orderItems.add(orderItem);
            } while (resultSet.next());
        });
        return orderItems;
    }
}
