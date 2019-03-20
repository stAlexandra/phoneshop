package com.es.phoneshop.web.util;

import com.es.core.model.cart.Cart;
import org.springframework.ui.Model;

public class MiniCartModelAttributeSetter {
    public static void addMiniCartInfoAttributes(Model model, Cart cart){
        model.addAttribute("cartNumItems", cart.getItems().size());
        model.addAttribute("cartTotalPrice", cart.getTotalPrice());
    }
}
