package com.es.core.service.discount;

import com.es.core.model.cart.Cart;
import com.es.core.model.discount.Discount;
import com.es.core.service.checkout.CartService;
import com.es.core.util.DiscountUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DiscountServiceImpl implements DiscountService {
    private CartService cartService;

    public boolean applyDiscount(@NotNull Discount discount) {
        if (discount.isEnabled()) {
            return applyDiscountBasedOnType(discount, true);
        } else return false;
    }

    public boolean applyDiscount(@NotNull Collection<Discount> discounts) {
        Map<Boolean, List<Discount>> appliedStatusToDiscountsMap = discounts.stream()
                .filter(Discount::isEnabled)
                .collect(Collectors.groupingBy(discount -> applyDiscountBasedOnType(discount, false)));    
        if (!CollectionUtils.isEmpty(appliedStatusToDiscountsMap)) {
            cartService.recalculateCart();
        }
        List<Discount> unappliedDiscounts = appliedStatusToDiscountsMap.get(false); // TODO can do smth with this info
        return CollectionUtils.isEmpty(unappliedDiscounts);
    }

    private boolean applyDiscountBasedOnType(Discount discount, boolean recalculate) {
        boolean applied = false;
        switch (discount.getApplicableFor()) {
            case CART: {
                Cart cart = cartService.getCart();
                applied = applyDiscountForCart(cart, discount);
                if (recalculate) {
                    cartService.recalculateCart();
                }
            }
            case PRODUCT: {
                // TODO add product discounts
                break;
            }
        }
        return applied;
    }
    
    private boolean applyDiscountForCart(Cart cart, Discount discount) {
        BigDecimal discountValue = DiscountUtil.getAbsoluteDiscountValue(cart.getTotalPrice(), discount);
        if (discountIsValid(cart, discountValue)) {
            cart.getDiscounts().add(discount);
            return true;
        }
        return false;
    }

    private boolean discountIsValid(Cart cart, BigDecimal discountValue) {
        // TODO implement
        return cart.getTotalPrice().compareTo(discountValue) > 0;
    }

    @Autowired
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }
}
