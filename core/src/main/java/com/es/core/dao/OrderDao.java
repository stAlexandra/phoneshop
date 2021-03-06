package com.es.core.dao;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;

import java.util.List;

public interface OrderDao {
    void save(Order order);
    Order getBySecureId(String secureId);
    Order getById(Long id);
    List<Order> getAll(String sortName, String sortOrder);
    void updateOrderStatus(Long id, OrderStatus orderStatus);
}
