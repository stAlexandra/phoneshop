package com.es.core.service.checkout;

import com.es.core.model.cart.Cart;
import com.es.core.model.discount.Discount;

import java.util.Set;

public interface CartDiscountService {
    boolean hasDiscount(Discount discount);
    boolean hasDiscount(Cart cart, Discount discount);

    Set<Discount> getInactiveDiscounts(Set<Discount> discounts);

    Set<Discount> getInactiveDiscounts(Cart cart, Set<Discount> discounts);
}
