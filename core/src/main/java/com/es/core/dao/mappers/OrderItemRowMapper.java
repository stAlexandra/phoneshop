package com.es.core.dao.mappers;

import com.es.core.model.order.OrderItem;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;


@Component
public class OrderItemRowMapper implements RowMapper<OrderItem> {
    @Override
    public OrderItem mapRow(ResultSet resultSet, int i) throws SQLException {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(resultSet.getLong("id"));
        orderItem.setQuantity(resultSet.getInt("quantity"));
        return orderItem;
    }
}
