package com.es.core.dao.impl;

import com.es.core.dao.OrderDao;
import com.es.core.dao.mappers.ColorRowMapper;
import com.es.core.dao.mappers.OrderItemRowMapper;
import com.es.core.dao.mappers.OrderRowMapper;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.phone.Color;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
@Repository
public class JdbcOrderDao implements OrderDao {
    private static final String SQL_INSERT_ORDER = "INSERT INTO orders (secureId, orderDate, subtotal, deliveryPrice, totalPrice, firstName, lastName, " +
            "deliveryAddress, contactPhoneNo, status) VALUES(:secureId, NOW(), :subtotal, :deliveryPrice, :totalPrice, :firstName, :lastName, " +
            ":deliveryAddress, :contactPhoneNo, :status)";
    private static final String SQL_GET_ORDER_BY_SECURE_ID = "SELECT * FROM orders o " +
            "JOIN orderItems oI ON o.id = oI.orderId JOIN phones ph ON oI.phoneId = ph.id " +
            "LEFT JOIN phone2color ph2c ON ph.id = ph2c.phoneId LEFT JOIN colors c ON ph2c.colorId = c.id " +
            "WHERE secureId = :secureId";
    private static final String SQL_GET_ORDER_BY_ID = "SELECT * FROM orders o " +
            "JOIN orderItems oI ON o.id = oI.orderId JOIN phones ph ON oI.phoneId = ph.id " +
            "LEFT JOIN phone2color ph2c ON ph.id = ph2c.phoneId LEFT JOIN colors c ON ph2c.colorId = c.id " +
            "WHERE o.id = :orderId";
    private static final String SQL_GET_ALL_ORDERS_SORTED = "SELECT * FROM orders o " +
            "JOIN orderItems oI ON o.id = oI.orderId JOIN phones ph ON oI.phoneId = ph.id " +
            "LEFT JOIN phone2color ph2c ON ph.id = ph2c.phoneId LEFT JOIN colors c ON ph2c.colorId = c.id " +
            "ORDER BY {0} {1}";
    private static final String SQL_UPDATE_ORDER_STATUS = "UPDATE orders SET status = :status WHERE id = :orderId";
    private static final String SQL_INSERT_ORDER_ITEM = "INSERT INTO orderItems (phoneId, orderId, quantity) VALUES (:phoneId, :orderId, :quantity)";
    private static final String ORDERS_ID_COL = "orders.id";
    private static final String ORDER_ITEMS_PHONE_ID_COL = "orderItems.phoneId";
    private static final String SECURE_ID_PARAM = "secureId";
    private static final String PHONE_ID_PARAM = "phoneId";
    private static final String ORDER_ID_PARAM = "orderId";
    private static final String STATUS_PARAM = "status";
    private static final String QUANTITY_PARAM = "quantity";

    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Resource
    private OrderRowMapper orderRowMapper;

    @Resource
    private OrderItemRowMapper orderItemRowMapper;

    @Resource
    private ColorRowMapper colorRowMapper;

    @Override
    public void save(Order order) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(order);
        parameterSource.registerSqlType(STATUS_PARAM, Types.VARCHAR);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(SQL_INSERT_ORDER, parameterSource, keyHolder);
        if (!keyHolder.getKeyList().isEmpty()) {
            order.setId(keyHolder.getKey().longValue());
        }

        saveOrderItems(order.getOrderItems());
    }

    @Transactional(readOnly = true)
    @Override
    public Order getBySecureId(String secureId) {
        MapSqlParameterSource params = new MapSqlParameterSource(SECURE_ID_PARAM, secureId);
        return getOrder(SQL_GET_ORDER_BY_SECURE_ID, params);
    }

    @Transactional(readOnly = true)
    @Override
    public Order getById(Long id) {
        MapSqlParameterSource params = new MapSqlParameterSource(ORDER_ID_PARAM, id);
        return getOrder(SQL_GET_ORDER_BY_ID, params);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Order> getAll(String sortName, String sortOrder) {
        String sqlWithParams = MessageFormat.format(SQL_GET_ALL_ORDERS_SORTED, sortName, sortOrder);

        return jdbcTemplate.query(sqlWithParams, resultSet -> {
            List<Order> orders = new ArrayList<>();
            if (resultSet.next()) {
                while (!resultSet.isAfterLast()) {
                    Order order = orderRowMapper.mapRow(resultSet, resultSet.getRow());

                    List<OrderItem> orderItems = retrieveOrderItems(resultSet, order);
                    order.setOrderItems(orderItems);
                    orders.add(order);
                }
            }
            return orders;
        });
    }

    @Override
    public void updateOrderStatus(Long id, OrderStatus orderStatus) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue(ORDER_ID_PARAM, id);
        parameterSource.addValue(STATUS_PARAM, orderStatus.name());

        jdbcTemplate.update(SQL_UPDATE_ORDER_STATUS, parameterSource);
    }

    private Order getOrder(String sqlGetOrder, SqlParameterSource parameterSource) {
        return jdbcTemplate.query(sqlGetOrder, parameterSource, resultSet -> {
            Order order = new Order();
            if (resultSet.next()) {
                order = orderRowMapper.mapRow(resultSet, 1);
                List<OrderItem> orderItems = retrieveOrderItems(resultSet, order);
                order.setOrderItems(orderItems);
            }
            return order;
        });
    }

    private List<OrderItem> retrieveOrderItems(ResultSet resultSet, Order order) throws SQLException {
        List<OrderItem> orderItems = new ArrayList<>();
        while (!resultSet.isAfterLast() && resultSet.getLong(ORDERS_ID_COL) == order.getId()) {
            OrderItem orderItem = orderItemRowMapper.mapRow(resultSet, resultSet.getRow());
            orderItem.setOrder(order);

            Set<Color> colorSet = new HashSet<>();
            do {
                colorSet.add(colorRowMapper.mapRow(resultSet, resultSet.getRow()));
            } while (resultSet.next() && orderItem.getPhone().getId() == resultSet.getLong(ORDER_ITEMS_PHONE_ID_COL));
            orderItem.getPhone().setColors(colorSet);
            orderItems.add(orderItem);
        }
        return orderItems;
    }

    private void saveOrderItems(List<OrderItem> orderItems) {
        MapSqlParameterSource[] batchParamSources = orderItems.stream().map(orderItem -> {
            MapSqlParameterSource parameterSource = new MapSqlParameterSource();
            parameterSource.addValue(PHONE_ID_PARAM, orderItem.getPhone().getId());
            parameterSource.addValue(ORDER_ID_PARAM, orderItem.getOrder().getId());
            parameterSource.addValue(QUANTITY_PARAM, orderItem.getQuantity());
            return parameterSource;
        }).toArray(MapSqlParameterSource[]::new);

        jdbcTemplate.batchUpdate(SQL_INSERT_ORDER_ITEM, batchParamSources);
    }
}
