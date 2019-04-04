package com.es.core.service.impl;

import com.es.core.dao.OrderDao;
import com.es.core.exception.OutOfStockException;
import com.es.core.model.cart.Cart;
import com.es.core.model.cart.CartItem;
import com.es.core.model.order.CustomerInfo;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.stock.Stock;
import com.es.core.service.CartService;
import com.es.core.service.OrderService;
import com.es.core.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Value("${delivery.price}")
    private BigDecimal deliveryPrice;

    private OrderDao orderDao;
    private StockService stockService;
    private CartService cartService;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, StockService stockService, CartService cartService) {
        this.orderDao = orderDao;
        this.stockService = stockService;
        this.cartService = cartService;
    }

    @Override
    public Order getOrderBySecureId(String secureId) {
        return orderDao.get(secureId);
    }

    @Override
    public Order createOrder(Cart cart, CustomerInfo customerInfo) throws OutOfStockException {
        checkForOutOfStockItems(cart);

        Order order = new Order();
        order.setSecureId(UUID.randomUUID().toString());
        setCartInfoToOrder(order, cart);
        setCustomerInfoToOrder(order, customerInfo);

        return order;
    }

    @Override
    public void placeOrder(Order order) {
        Map<Long, Stock> stocks = stockService.getStocks(order.getOrderItems().stream()
                .map(item -> item.getPhone().getId()).collect(Collectors.toList()));

        order.getOrderItems().forEach(orderItem -> {
            Stock stock = stocks.get(orderItem.getPhone().getId());
            stock.setStock(stock.getStock() - orderItem.getQuantity());
            stockService.updateStock(stock);
        });
        cartService.clearCart();

        orderDao.save(order);
    }

    private void checkForOutOfStockItems(Cart cart) throws OutOfStockException {
        Map<Long, Integer> phoneIdToAvailableQuantityMap = stockService.getPhoneIdToAvailableQuantityMap(cart);

        List<String> outOfStockPhoneModels = new ArrayList<>();
        List<CartItem> outOfStockItems = cart.getItems().stream()
                .filter(item -> item.getQuantity() > phoneIdToAvailableQuantityMap.get(item.getPhone().getId()))
                .peek(item -> outOfStockPhoneModels.add(item.getPhone().getModel()))
                .collect(Collectors.toList());

        if (cartService.removeAll(outOfStockItems)) {
            throw new OutOfStockException(String.join(", ", outOfStockPhoneModels));
        }
    }

    private void setCustomerInfoToOrder(Order order, CustomerInfo customerInfo) {
        order.setFirstName(customerInfo.getFirstName());
        order.setLastName(customerInfo.getLastName());
        order.setDeliveryAddress(customerInfo.getDeliveryAddress());
        order.setContactPhoneNo(customerInfo.getContactPhoneNo());
    }

    private void setCartInfoToOrder(Order order, Cart cart) {
        order.setStatus(OrderStatus.NEW);
        order.setSubtotal(cart.getTotalPrice());
        order.setDeliveryPrice(deliveryPrice);
        order.setTotalPrice(deliveryPrice.add(cart.getTotalPrice()));

        List<OrderItem> orderItems = cart.getItems().stream().map(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setPhone(cartItem.getPhone());
            orderItem.setQuantity(cartItem.getQuantity().intValue());
            orderItem.setOrder(order);
            return orderItem;
        }).collect(Collectors.toList());
        order.setOrderItems(orderItems);
    }
}
