package com.es.core.service.checkout;

import com.es.core.model.cart.Cart;

import java.util.Collection;
import java.util.Map;

public interface CartService {
    Cart getCart();

    void addProduct(Long id, Long quantity);

    void update(Map<Long, Long> items);

    boolean remove(Long id);

    void remove(Collection<Long> idList);

    void clearCart();

    void recalculateCart();

}
