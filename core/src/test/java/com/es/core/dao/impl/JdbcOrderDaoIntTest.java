package com.es.core.dao.impl;

import com.es.core.model.order.Order;
import com.es.core.model.order.OrderItem;
import com.es.core.model.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = "classpath:context/applicationContext-core.xml")
@Sql("classpath:db/testdata-phones.sql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class JdbcOrderDaoIntTest {
    private static final long PRODUCT1_ID = 1000L;
    private static final long PRODUCT2_ID = 1001L;

    @Autowired
    private JdbcOrderDao jdbcOrderDao;

    private Product product1;
    private Product product2;

    @Before
    public void setUp() {
        product1 = Mockito.mock(Product.class);
        product2 = Mockito.mock(Product.class);
        Mockito.when(product1.getId()).thenReturn(PRODUCT1_ID);
        Mockito.when(product2.getId()).thenReturn(PRODUCT2_ID);

    }

    @Test
    public void save() {
        Order order = new Order();
        order.setSecureId("123456789");

        OrderItem item1 = new OrderItem();
        item1.setProduct(product1);
        item1.setQuantity(1);
        item1.setOrder(order);
        OrderItem item2 = new OrderItem();
        item2.setProduct(product2);
        item2.setQuantity(2);
        item2.setOrder(order);

        order.setOrderItems(Arrays.asList(item1, item2));

        jdbcOrderDao.save(order);

        assertNotNull(order.getId());
    }

    @Test
    public void getBySecureId() {
        String secureId = "985828284";
        Order order = jdbcOrderDao.getBySecureId(secureId);

        assertNotNull(order.getId());
        assertEquals(secureId, order.getSecureId());
        assertFalse(order.getOrderItems().isEmpty());
    }

    @Test
    public void getAll() {
        List<Order> orders = jdbcOrderDao.getAll("orderDate", "desc");

        assertTrue(!orders.isEmpty());
        orders.forEach(order -> assertFalse(order.getOrderItems().isEmpty()));
    }
}