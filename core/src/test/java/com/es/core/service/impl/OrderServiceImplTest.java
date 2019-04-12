package com.es.core.service.impl;

import com.es.core.dao.OrderDao;
import com.es.core.model.cart.Cart;
import com.es.core.model.order.CustomerInfo;
import com.es.core.model.order.Order;
import com.es.core.model.order.OrderStatus;
import com.es.core.service.CartService;
import com.es.core.service.StockService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest {
    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private StockService stockService;

    @Mock
    private CartService cartService;

    @Mock
    private OrderDao orderDao;

    @Mock
    private BigDecimal deliveryPrice;

    @Test
    public void getOrderBySecureId() {
        String secureId = "secureId";
        orderService.getOrderBySecureId(secureId);
        verify(orderDao).get(secureId);
    }

    @Test
    public void createOrder() {
        Cart cart = mock(Cart.class);
        when(cart.getTotalPrice()).thenReturn(BigDecimal.ONE);
        CustomerInfo customerInfo = mock(CustomerInfo.class);

        Order order = orderService.createOrder(cart, customerInfo);

        assertNotNull(order.getSecureId());
        assertNotNull(order.getStatus());
        assertEquals(OrderStatus.NEW, order.getStatus());
    }

    @Test
    public void placeOrder() {
        Order order = mock(Order.class);
        orderService.placeOrder(order);

        verify(stockService).updateStocks(anyCollection());
        verify(cartService).clearCart();
        verify(orderDao).save(order);
    }
}