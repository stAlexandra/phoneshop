package com.es.core.service.impl;

import com.es.core.dao.PhoneDao;
import com.es.core.model.cart.Cart;
import com.es.core.model.phone.Phone;
import com.es.core.service.checkout.HttpSessionCartService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionCartServiceTest {
    private static final Long PRODUCT_ID = 1L;
    private static final BigDecimal PRODUCT_PRICE = new BigDecimal(100.0);

    @InjectMocks
    private HttpSessionCartService cartService;

    @Spy
    private Cart cart;

    @Mock
    private PhoneDao productDao;

    @Mock
    private Phone product;

    @Before
    public void setUp(){
        when(productDao.get(PRODUCT_ID)).thenReturn(product);
        when(product.getPrice()).thenReturn(PRODUCT_PRICE);
        when(product.getId()).thenReturn(PRODUCT_ID);
    }

    @Test
    public void addNewProductToCart() {
        cartService.addProduct(PRODUCT_ID, 1L);
        assertEquals(1, cart.getItems().size());
        assertEquals(PRODUCT_PRICE, cart.getItems().get(0).getPhone().getPrice());
    }

    @Test
    public void addMoreOfExistingProductToCart(){
        // given
        long quantity1 = 1L;
        long quantity2 = 2L;

        // when
        cartService.addProduct(PRODUCT_ID, quantity1);
        cartService.addProduct(PRODUCT_ID, quantity2);

        // then
        assertEquals(1, cart.getItems().size());
        assertEquals(quantity1 + quantity2, cart.getItems().get(0).getQuantity().longValue());
        assertEquals(PRODUCT_PRICE.multiply(BigDecimal.valueOf(quantity1 + quantity2)), cart.getTotalPrice());
    }

    @Test
    public void remove() {
        cartService.addProduct(PRODUCT_ID, 3L);
        assertEquals(1, cart.getItems().size());

        boolean removed = cartService.remove(PRODUCT_ID);

        assertEquals(0, cart.getItems().size());
        assertTrue(removed);
    }
}