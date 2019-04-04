package com.es.core.dao;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;

import java.util.List;

public interface OrderItemDao {
    void save(OrderItem orderItem);
    OrderItem get(Long id);
    List<OrderItem> getItemsForOrder(Long orderId);
    List<OrderItem> getItemsForOrder(Order order);
}
