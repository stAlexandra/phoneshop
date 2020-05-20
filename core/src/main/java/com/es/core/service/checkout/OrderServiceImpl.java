package com.es.core.service.checkout;

import com.es.core.dao.OrderDao;
import com.es.core.model.cart.Cart;
import com.es.core.model.order.CustomerInfo;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.order.OrderStatus;
import com.es.core.model.stock.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private BigDecimal deliveryPrice;
    private OrderDao orderDao;
    private StockService stockService;
    private CartService cartService;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, StockService stockService, CartService cartService,
                            @Value("${delivery.price}") BigDecimal deliveryPrice) {
        this.orderDao = orderDao;
        this.stockService = stockService;
        this.cartService = cartService;
        this.deliveryPrice = deliveryPrice;
    }

    @Override
    public Order getOrderBySecureId(String secureId) {
        return orderDao.getBySecureId(secureId);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderDao.getById(id);
    }

    @Override
    public Order createOrder(Cart cart, CustomerInfo customerInfo) {
        Order order = new Order();
        setCartInfoToOrder(order, cart);
        setCustomerInfoToOrder(order, customerInfo);
        order.setSecureId(UUID.randomUUID().toString());
        order.setDeliveryPrice(deliveryPrice);
        order.setTotalPrice(deliveryPrice.add(cart.getTotalPrice()));

        return order;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public void placeOrder(Order order) {
        Map<Long, Stock> phoneIdToStock = stockService.getStocksMap(order.getOrderItems().stream()
                .map(item -> item.getProduct().getId())
                .collect(Collectors.toList()));

        order.getOrderItems().forEach(orderItem -> {
            Stock stock = phoneIdToStock.get(orderItem.getProduct().getId());
            stock.setStock(stock.getStock() - orderItem.getQuantity());
        });
        stockService.updateStocks(phoneIdToStock.values());
        cartService.clearCart();

        orderDao.save(order);
    }

    @Override
    public List<Order> getAllOrdersSortedByDate() {
        return orderDao.getAll("orderDate", "desc");
    }

    @Override
    public void updateOrderStatus(Long id, OrderStatus status) {
        orderDao.updateOrderStatus(id, status);
    }

    private void setCustomerInfoToOrder(Order order, CustomerInfo customerInfo) {
        order.setFirstName(customerInfo.getFirstName());
        order.setLastName(customerInfo.getLastName());
        order.setDeliveryAddress(customerInfo.getDeliveryAddress());
        order.setContactPhoneNo(customerInfo.getContactPhoneNo());
    }

    private void setCartInfoToOrder(Order order, Cart cart) {
        order.setSubtotal(cart.getTotalPrice());

        List<OrderItem> orderItems = cart.getItems().stream()
                .map(cartItem -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setProduct(cartItem.getProduct());
                    orderItem.setQuantity(cartItem.getQuantity().intValue());
                    orderItem.setOrder(order);
                    return orderItem;
                })
                .collect(Collectors.toList());
        order.setOrderItems(orderItems);
    }
}
