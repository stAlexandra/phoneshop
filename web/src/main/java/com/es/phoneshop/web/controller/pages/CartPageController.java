package com.es.phoneshop.web.controller.pages;

import com.es.core.service.CartService;
import com.es.phoneshop.web.dataview.UpdateCartItemRequestData;
import com.es.phoneshop.web.dataview.UpdateCartRequestData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CartPageController {
    @Resource(name = "httpSessionCartService")
    private CartService cartService;

    @GetMapping(value = "/cart")
    public String getCart(Model model) {
        model.addAttribute("cart", cartService.getCart());
        model.addAttribute("updateCartRequestData", new UpdateCartRequestData());
        return "cart";
    }

    @PutMapping(value = "/cart")
    public String updateCart(@Valid @ModelAttribute UpdateCartRequestData updateCartRequestData, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "cart";
        }
        Map<Long, Long> phoneIdToQuantity = new HashMap<>();
        for(UpdateCartItemRequestData cartItem : updateCartRequestData.getCartItemDataList()){
            phoneIdToQuantity.put(cartItem.getPhoneId(), cartItem.getQuantity());
        }
        cartService.update(phoneIdToQuantity);

        return "redirect:/cart";
    }

    @DeleteMapping(value = "/cart/{id:[\\d]+}")
    public String deleteItem(@PathVariable("id") Long phoneId, ModelMap model){
        cartService.remove(phoneId);
        return "redirect:/cart";
    }

}
