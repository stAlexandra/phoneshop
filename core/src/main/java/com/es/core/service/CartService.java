package com.es.core.service;

import com.es.core.model.cart.Cart;
import com.es.core.model.cart.CartItem;

import java.util.List;
import java.util.Map;

public interface CartService {
    Cart getCart();

    void addPhone(Long phoneId, Long quantity);

    void update(Map<Long, Long> items);

    boolean remove(Long phoneId);

    boolean removeAll(List<CartItem> cartItems);

    void clearCart();
}
