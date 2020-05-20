package com.es.core.dao.mappers;

import com.es.core.model.order.OrderItem;
import com.es.core.model.product.Product;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class OrderItemRowMapper implements RowMapper<OrderItem> {
    @Resource
    private RowMapper<Product> productRowMapper;

    @Override
    public OrderItem mapRow(ResultSet resultSet, int i) throws SQLException {
        OrderItem orderItem = new OrderItem();
        Product product = productRowMapper.mapRow(resultSet, resultSet.getRow());
        orderItem.setQuantity(resultSet.getInt("quantity"));
        orderItem.setProduct(product);
        return orderItem;
    }
}
