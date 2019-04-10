package com.es.core.dao.impl;

import com.es.core.dao.OrderDao;
import com.es.core.dao.mappers.OrderItemRowMapper;
import com.es.core.dao.mappers.OrderRowMapper;
import com.es.core.dao.mappers.PhoneRowMapper;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.phone.Phone;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.Types;
import java.util.*;

@Repository
public class JdbcOrderDao implements OrderDao {
    private static final String SQL_INSERT_ORDER = "INSERT INTO orders (secureId, subtotal, deliveryPrice, totalPrice, firstName, lastName, " +
            "deliveryAddress, contactPhoneNo, status) VALUES(:secureId, :subtotal, :deliveryPrice, :totalPrice, :firstName, :lastName, " +
            ":deliveryAddress, :contactPhoneNo, :status)";
    private static final String SQL_GET_ORDER_BY_SECURE_ID = "SELECT * FROM orders o " +
            "JOIN orderItems oI ON o.id = oI.orderId JOIN phones ph ON oI.phoneId = ph.id " +
            "WHERE secureId = :secureId";
    private static final String SQL_UPDATE_ORDER_STATUS = "UPDATE orders SET status = :status WHERE id = :id";
    private static final String SQL_INSERT_ORDER_ITEM = "INSERT INTO orderItems (phoneId, orderId, quantity) VALUES (:phoneId, :orderId, :quantity)";
    private static final String SQL_GET_ORDER_ITEMS_ID = "SELECT id FROM orderItems WHERE orderItems.orderId = :orderId";

    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Resource
    private OrderRowMapper orderRowMapper;

    @Resource
    private OrderItemRowMapper orderItemRowMapper;

    @Resource
    private PhoneRowMapper phoneRowMapper;

    @Override
    public void save(Order order) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(order);
        parameterSource.registerSqlType("status", Types.VARCHAR);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(SQL_INSERT_ORDER, parameterSource, keyHolder);
        if (!keyHolder.getKeyList().isEmpty()) {
            order.setId(keyHolder.getKey().longValue());
        }

        saveOrderItems(order.getOrderItems(), order.getId());
    }

    @Override
    public Order get(String secureId) {
        Map<String, String> params = Collections.singletonMap("secureId", secureId);
        return jdbcTemplate.query(SQL_GET_ORDER_BY_SECURE_ID, params, resultSet -> {
            Order order = new Order();
            List<OrderItem> orderItems = new ArrayList<>();
            while (resultSet.next()){
                if (resultSet.isFirst()) {
                    order = orderRowMapper.mapRow(resultSet, 1);
                }
                Phone phone = phoneRowMapper.mapRow(resultSet, resultSet.getRow());
                OrderItem orderItem = orderItemRowMapper.mapRow(resultSet, resultSet.getRow());
                orderItem.setPhone(phone);
                orderItem.setOrder(order);
                orderItems.add(orderItem);
            }
            order.setOrderItems(orderItems);
            return order;
        });
    }

    @Override
    public void updateOrderStatus(Long id, OrderStatus orderStatus) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", id);
        parameterSource.addValue("status", orderStatus.name());

        jdbcTemplate.update(SQL_UPDATE_ORDER_STATUS, parameterSource);
    }

    private void saveOrderItems(List<OrderItem> orderItems, Long orderId) {
        MapSqlParameterSource[] batchParamSources = orderItems.stream().map(orderItem -> {
            MapSqlParameterSource parameterSource = new MapSqlParameterSource();
            parameterSource.addValue("phoneId", orderItem.getPhone().getId());
            parameterSource.addValue("orderId", orderItem.getOrder().getId());
            parameterSource.addValue("quantity", orderItem.getQuantity());
            return parameterSource;
        }).toArray(MapSqlParameterSource[]::new);

        jdbcTemplate.batchUpdate(SQL_INSERT_ORDER_ITEM, batchParamSources);

        jdbcTemplate.query(SQL_GET_ORDER_ITEMS_ID, Collections.singletonMap("orderId", orderId), resultSet -> {
            for (int i = 0; !resultSet.isAfterLast() && i < orderItems.size(); i++) {
                orderItems.get(i).setId(resultSet.getLong("id"));
                resultSet.next();
            }
        });
    }
}
