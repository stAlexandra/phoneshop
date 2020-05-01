package com.es.core.service.businesslogic;

import com.es.core.model.cart.Cart;
import com.es.core.model.discount.Discount;
import com.es.core.service.CartService;
import com.es.core.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.security.Principal;
import java.util.Set;

@Service
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    private CartService cartService;

    public boolean applyDiscount(@NotNull Discount discount) {
        boolean applied = false;
        if (discount.isEnabled()) {
            switch (discount.getApplicableFor()) {
                case CART: {
                    Cart cart = cartService.getCart();
                    applied = applyDiscountForCart(cart, discount);
                    cartService.recalculateCart();
                }
                case PRODUCT: {
                    break;
                }
            }
        }
        return applied;
    }

    private boolean applyDiscountForCart(Cart cart, Discount discount) {
//        BigDecimal oldTotalPrice = cart.getTotalPrice();
        BigDecimal oldTotalDiscount = cart.getTotalDiscount();
        BigDecimal discountValue = getAbsoluteDiscountValue(cart, discount);
        if (discountIsValid(cart, discount)) {
            // TODO: create field total discount in cart
            cart.setTotalDiscount(discountValue.add(oldTotalDiscount));
//            cart.setTotalPrice(oldTotalPrice.subtract(discountValue));
            return true;
        }
        return false;
    }

    private boolean discountIsValid(Cart cart, Discount discount) {
        return true;
        // TODO
    }

    private BigDecimal getAbsoluteDiscountValue(Cart cart, Discount discount) {
        BigDecimal totalPrice = cart.getTotalPrice();
        switch (discount.getValueType()) {
            case AMOUNT: {
                return discount.getValue();
            }
            case PERCENTAGE: {
//                BigDecimal rate = BigDecimal.valueOf(0.01);
                return totalPrice.multiply(discount.getValue());
            }
            default: {
                return BigDecimal.ZERO;
            }
        }
    }

    private BigDecimal getPercentageDiscountValue(Cart cart, Discount discount) {
        BigDecimal totalPrice = cart.getTotalPrice();

        switch (discount.getValueType()) {
            case AMOUNT: {
                return discount.getValue().divide(totalPrice, new MathContext(2, RoundingMode.FLOOR));
            }
            case PERCENTAGE: {
                return discount.getValue();
            }
            default: {
                return BigDecimal.ZERO;
            }
        }
    }
}
