package com.es.core.service.checkout;

import com.es.core.model.cart.Cart;
import com.es.core.model.order.CustomerInfo;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;

import java.util.List;

public interface OrderService {
    Order createOrder(Cart cart, CustomerInfo customerInfo);

    void placeOrder(Order order);

    Order getOrderBySecureId(String secureId);

    Order getOrderById(Long id);

    List<Order> getAllOrdersSortedByDate();

    void updateOrderStatus(Long id, OrderStatus status);
}
