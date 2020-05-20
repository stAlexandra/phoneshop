package com.es.core.service.checkout;

import com.es.core.model.cart.Cart;
import com.es.core.model.cart.CartItem;
import com.es.core.model.product.Product;
import com.es.core.service.product.ProductService;
import com.es.core.util.DiscountUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HttpSessionCartService implements CartService {
    @Autowired
    private ProductService productService;

    @Autowired
    private Cart cart;

    @Override
    public Cart getCart() {
        return this.cart;
    }

    @Override
    public void addProduct(Long id, Long quantity) {
        Optional<CartItem> optionalCartItem = cart
                .getItems().stream()
                .filter(item -> id.equals(item.getProduct().getId()))
                .findAny();

        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            Product product = productService.get(id);
            cart.getItems().add(new CartItem(product, quantity));
        }
        recalculateCart();
    }

    @Override
    public void update(Map<Long, Long> phoneIdToQuantity) {
        List<CartItem> updatedCartItems = cart.getItems().stream()
                .filter(item -> phoneIdToQuantity.containsKey(item.getProduct().getId()))
                .peek(item -> item.setQuantity(phoneIdToQuantity.get(item.getProduct().getId())))
                .collect(Collectors.toList());
        cart.setItems(updatedCartItems);
        recalculateCart();
    }

    @Override
    public boolean remove(Long id) {
        boolean isRemoved = cart.getItems().removeIf(item -> id.equals(item.getProduct().getId()));
        recalculateCart();
        return isRemoved;
    }

    @Override
    public void remove(Collection<Long> idList) {
        List<CartItem> remainedItems = cart.getItems().stream()
                .filter(item -> !idList.contains(item.getProduct().getId()))
                .collect(Collectors.toList());
        cart.setItems(remainedItems);
        recalculateCart();
    }

    @Override
    public void clearCart() {
        cart.getItems().clear();
        recalculateCart();
    }

    public void recalculateCart() {
        BigDecimal totalItemsPrice = cart.getItems().stream()
                .map(cartItem -> cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        cart.setSubtotalPrice(totalItemsPrice);

        BigDecimal totalDiscount = cart.getDiscounts().stream()
                .filter(Objects::nonNull)
                .map(discount -> DiscountUtil.getAbsoluteDiscountValue(totalItemsPrice, discount))
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        cart.setTotalDiscount(totalDiscount);

        if (totalItemsPrice.compareTo(totalDiscount) > 0) {
            cart.setTotalPrice(totalItemsPrice.subtract(totalDiscount));
        } else {
            cart.setTotalPrice(totalItemsPrice);
        }
    }
}
