package com.es.core.service.checkout;

import com.es.core.model.cart.Cart;

import java.util.Collection;
import java.util.Map;

public interface CartService {
    Cart getCart();

    void addPhone(Long phoneId, Long quantity);

    void update(Map<Long, Long> items);

    boolean remove(Long phoneId);

    void remove(Collection<Long> phoneIdList);

    void clearCart();

    void recalculateCart();

}
