package com.es.core.service.impl;

import com.es.core.dao.PhoneDao;
import com.es.core.model.cart.Cart;
import com.es.core.model.cart.CartItem;
import com.es.core.model.phone.Phone;
import com.es.core.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Service
public class HttpSessionCartService implements CartService {
    @Resource(name = "jdbcPhoneDao")
    private PhoneDao phoneDao;

    @Autowired
    @Qualifier("cart")
    private Cart cart;

    @Override
    public Cart getCart() {
        return this.cart;
    }

    @Override
    public void addPhone(Long phoneId, Long quantity) {
        Optional<CartItem> optionalCartItem = cart.getItems().stream()
                .filter(item -> phoneId.equals(item.getPhone().getId())).findAny();

        if(optionalCartItem.isPresent()){
            CartItem cartItem = optionalCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            Phone phone = phoneDao.get(phoneId);
            cart.getItems().add(new CartItem(phone, quantity));
        }
        recalculateCart();
    }

    @Override
    public void update(Map<Long, Long> items) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public boolean remove(Long phoneId) {
        boolean isRemoved = cart.getItems().removeIf(item -> phoneId.equals(item.getPhone().getId()));
        recalculateCart();
        return isRemoved;
    }

    private void recalculateCart(){
        BigDecimal totalPrice = cart.getItems().stream()
                .map(cartItem -> cartItem.getPhone().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        cart.setTotalPrice(totalPrice);
    }
}
