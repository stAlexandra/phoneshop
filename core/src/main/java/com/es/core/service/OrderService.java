package com.es.core.service;

import com.es.core.exception.OutOfStockException;
import com.es.core.model.cart.Cart;
import com.es.core.model.order.CustomerInfo;
import com.es.core.model.order.Order;

public interface OrderService {
    Order createOrder(Cart cart, CustomerInfo customerInfo) throws OutOfStockException;

    void placeOrder(Order order);

    Order getOrderBySecureId(String secureId);
}
