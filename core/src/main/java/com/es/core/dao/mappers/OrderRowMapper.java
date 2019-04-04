package com.es.core.dao.mappers;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class OrderRowMapper implements RowMapper<Order> {
    @Override
    public Order mapRow(ResultSet resultSet, int i) throws SQLException {
        Order order = new Order();
        order.setId(resultSet.getLong("id"));
        order.setSecureId(resultSet.getString("secureId"));
        order.setFirstName(resultSet.getString("firstName"));
        order.setLastName(resultSet.getString("lastName"));
        order.setSubtotal(resultSet.getBigDecimal("subtotal"));
        order.setDeliveryPrice(resultSet.getBigDecimal("deliveryPrice"));
        order.setTotalPrice(resultSet.getBigDecimal("totalPrice"));
        order.setDeliveryAddress(resultSet.getString("deliveryAddress"));
        order.setContactPhoneNo(resultSet.getString("contactPhoneNo"));
        order.setStatus(OrderStatus.valueOf(resultSet.getString("status")));
        return order;
    }
}
