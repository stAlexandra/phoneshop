package com.es.core.dao;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;

public interface OrderDao {
    void save(Order order);
    Order get(String secureId);
    void updateOrderStatus(Long id, OrderStatus orderStatus);
}
