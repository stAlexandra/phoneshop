package com.es.core.dao.mappers;

import com.es.core.model.order.OrderItem;
import com.es.core.model.phone.Phone;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class OrderItemRowMapper implements RowMapper<OrderItem> {
    @Resource
    private PhoneRowMapper phoneRowMapper;

    @Override
    public OrderItem mapRow(ResultSet resultSet, int i) throws SQLException {
        OrderItem orderItem = new OrderItem();
        Phone phone = phoneRowMapper.mapRow(resultSet, resultSet.getRow());
        orderItem.setQuantity(resultSet.getInt("quantity"));
        orderItem.setPhone(phone);
        return orderItem;
    }
}
