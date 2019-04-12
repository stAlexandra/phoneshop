package com.es.phoneshop.web.controller.pages;

import com.es.core.model.cart.Cart;
import com.es.core.service.CartService;
import com.es.phoneshop.web.dataview.UpdateCartItemRequestData;
import com.es.phoneshop.web.dataview.UpdateCartRequestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cart")
public class CartPageController {
    private static final String VIEW_NAME = "cart";

    @Autowired
    private CartService cartService;

    @GetMapping
    public String getCart(Model model) {
        Cart cart = cartService.getCart();
        model.addAttribute("cart", cart);
        model.addAttribute("updateCartRequestData", new UpdateCartRequestData(cart));
        return VIEW_NAME;
    }

    @PutMapping
    public ModelAndView updateCart(@Valid @ModelAttribute UpdateCartRequestData updateCartRequestData, BindingResult bindingResult,
                                   ModelMap model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("cart", cartService.getCart());
            return new ModelAndView(VIEW_NAME, model, HttpStatus.BAD_REQUEST);
        }
        Map<Long, Long> phoneIdToQuantity = updateCartRequestData.getCartItemDataList().stream()
                .collect(Collectors.toMap(UpdateCartItemRequestData::getPhoneId, UpdateCartItemRequestData::getQuantity));
        cartService.update(phoneIdToQuantity);
        return new ModelAndView("redirect:/" + VIEW_NAME);
    }

    @DeleteMapping(path = "/{id}")
    public String deleteItem(@PathVariable("id") Long phoneId) {
        cartService.remove(phoneId);
        return "redirect:/" + VIEW_NAME;
    }

}
