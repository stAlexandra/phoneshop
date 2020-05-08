package com.es.core.service.checkout;

import com.es.core.model.cart.Cart;
import com.es.core.model.discount.Discount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CartDiscountServiceImpl implements CartDiscountService {
    private CartService cartService;

    @Override
    public boolean hasDiscount(Discount discount) {
        return hasDiscount(cartService.getCart(), discount);
    }

    @Override
    public boolean hasDiscount(@NotNull Cart cart, Discount discount) {
        return cart.getDiscounts().contains(discount);
    }

    @Override
    public Set<Discount> getInactiveDiscounts(Set<Discount> discounts) {
        return getInactiveDiscounts(cartService.getCart(), discounts);
    }

    @Override
    public Set<Discount> getInactiveDiscounts(@NotNull Cart cart, Set<Discount> discounts) {
        if (!CollectionUtils.isEmpty(cart.getDiscounts())) {
            return discounts.stream()
                    .filter(discount -> !cart.getDiscounts().contains(discount))
                    .collect(Collectors.toSet());
        } else return new HashSet<>(discounts);
    }

    @Autowired
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }
}
