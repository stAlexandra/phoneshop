package com.es.core.service.impl;

import com.es.core.dao.PhoneDao;
import com.es.core.model.cart.Cart;
import com.es.core.model.phone.Phone;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionCartServiceTest {
    private static final Long PHONE_ID = 1L;
    private static final BigDecimal PHONE_PRICE = new BigDecimal(100.0);

    @InjectMocks
    private HttpSessionCartService cartService;

    @Spy
    private Cart cart;

    @Mock
    private PhoneDao phoneDao;

    @Mock
    private Phone phone;

    @Before
    public void setUp(){
        when(phoneDao.get(PHONE_ID)).thenReturn(phone);
        when(phone.getPrice()).thenReturn(PHONE_PRICE);
        when(phone.getId()).thenReturn(PHONE_ID);
    }

    @Test
    public void addNewPhoneToCart() {
        cartService.addPhone(PHONE_ID, 1L);
        assertEquals(1, cart.getItems().size());
        assertEquals(PHONE_PRICE, cart.getItems().get(0).getPhone().getPrice());
    }

    @Test
    public void addMoreOfExistingPhoneToCart(){
        long quantity1 = 1L;
        long quantity2 = 2L;
        cartService.addPhone(PHONE_ID, quantity1);
        cartService.addPhone(PHONE_ID, quantity2);

        assertEquals(1, cart.getItems().size());
        assertEquals(quantity1 + quantity2, cart.getItems().get(0).getQuantity().longValue());
        assertEquals(PHONE_PRICE.multiply(BigDecimal.valueOf(quantity1 + quantity2)), cart.getTotalPrice());
    }

    @Test
    public void remove() {
        cartService.addPhone(PHONE_ID, 3L);
        assertEquals(1, cart.getItems().size());

        boolean removed = cartService.remove(PHONE_ID);

        assertEquals(0, cart.getItems().size());
        assertTrue(removed);
    }
}