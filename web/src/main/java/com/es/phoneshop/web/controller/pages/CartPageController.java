package com.es.phoneshop.web.controller.pages;

import com.es.core.model.cart.Cart;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import st.alexandra.facades.CartFacade;
import st.alexandra.facades.PromotionsFacade;
import st.alexandra.facades.UserFacade;
import st.alexandra.facades.dto.UpdateCartRequestData;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/cart")
public class CartPageController {
    private static final String VIEW_NAME = "cart";

    @Resource
    private CartFacade cartFacade;
    @Resource
    private PromotionsFacade promotionsFacade;
    @Resource
    private UserFacade userFacade;

    @GetMapping
    public String getCart(Model model, Principal principal, @RequestParam(required = false) Boolean couponActivated) {
        Cart cart = cartFacade.getCart();
        if (principal != null) {
            promotionsFacade.fetchAndApplyCartDiscounts(principal.getName(), cart);
        }

        model.addAttribute("cart", cart);
        model.addAttribute("updateCartRequestData", new UpdateCartRequestData(cart));
        model.addAttribute("user", userFacade.getUserData(principal));
        if (couponActivated != null) {
            addCouponInfo(couponActivated, model);
        }
        return VIEW_NAME;
    }

    @PutMapping
    public ModelAndView updateCart(@Valid @ModelAttribute UpdateCartRequestData updateCartRequestData, BindingResult bindingResult,
                                   Model model, Principal principal) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userFacade.getUserData(principal));
            model.addAttribute("cart", cartFacade.getCart());
            return new ModelAndView(VIEW_NAME, model.asMap(), HttpStatus.BAD_REQUEST);
        }
        cartFacade.update(updateCartRequestData);
        return new ModelAndView("redirect:/" + VIEW_NAME);
    }

    @DeleteMapping(path = "/{id}")
    public String deleteItem(@PathVariable("id") Long phoneId) {
        cartFacade.remove(phoneId);
        return "redirect:/" + VIEW_NAME;
    }

    private void addCouponInfo(boolean couponActivated, Model model) {
        if (couponActivated) {
            model.addAttribute("couponActivatedMsg", "Coupon is successfully activated.");
        } else {
            model.addAttribute("couponErrorMsg", "Coupon could not be activated.");
        }
    }

}
