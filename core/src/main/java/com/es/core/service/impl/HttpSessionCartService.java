package com.es.core.service.impl;

import com.es.core.dao.PhoneDao;
import com.es.core.model.cart.Cart;
import com.es.core.model.cart.CartItem;
import com.es.core.model.phone.Phone;
import com.es.core.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HttpSessionCartService implements CartService {
    @Autowired
    private PhoneDao phoneDao;

    @Autowired
    private Cart cart;

    @Override
    public Cart getCart() {
        return this.cart;
    }

    @Override
    public void addPhone(Long phoneId, Long quantity) {
        Optional<CartItem> optionalCartItem = cart
                .getItems().stream()
                .filter(item -> phoneId.equals(item.getPhone().getId())).findAny();

        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            Phone phone = phoneDao.get(phoneId);
            cart.getItems().add(new CartItem(phone, quantity));
        }
        recalculateCart();
    }

    @Override
    public void update(Map<Long, Long> phoneIdToQuantity) {
        List<CartItem> updatedCartItems = cart.getItems().stream().filter(item -> phoneIdToQuantity
                .containsKey(item.getPhone().getId()))
                .peek(item -> item.setQuantity(phoneIdToQuantity.get(item.getPhone().getId())))
                .collect(Collectors.toList());
        cart.setItems(updatedCartItems);
        recalculateCart();
    }

    @Override
    public boolean remove(Long phoneId) {
        boolean isRemoved = cart.getItems().removeIf(item -> phoneId.equals(item.getPhone().getId()));
        recalculateCart();
        return isRemoved;
    }

    private void recalculateCart() {
        BigDecimal totalPrice = cart.getItems().stream()
                .map(cartItem -> cartItem.getPhone().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        cart.setTotalPrice(totalPrice);
    }
}
