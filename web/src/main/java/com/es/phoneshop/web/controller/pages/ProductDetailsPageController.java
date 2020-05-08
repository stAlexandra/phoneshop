package com.es.phoneshop.web.controller.pages;

import com.es.core.dao.PhoneDao;
import com.es.core.exception.PhonesNotFoundException;
import com.es.core.model.user.User;
import com.es.core.service.checkout.CartService;
import com.es.core.service.user.UserLevelService;
import com.es.core.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.security.Principal;

@Controller
@RequestMapping("/productDetails")
public class ProductDetailsPageController {
    private static final String VIEW_NAME = "productDetails";
    private static final String PHONE_NOT_FOUND_VIEW_NAME = "phoneNotFound";
    @Resource
    private PhoneDao phoneDao;
    @Resource
    private CartService cartService;
    @Resource
    private UserService userService;
    @Resource
    private UserLevelService userLevelService;

    @ExceptionHandler(PhonesNotFoundException.class)
    public String handlePhonesNotFoundException(PhonesNotFoundException e) {
        return PHONE_NOT_FOUND_VIEW_NAME;
    }

    @GetMapping(path = "/{id}")
    public String showProductDetails(@PathVariable("id") Long phoneId, Model model, Principal principal) {
        addUserAttributes(model, principal);
        model.addAttribute("phone", phoneDao.get(phoneId));
        model.addAttribute("cart", cartService.getCart());
        return VIEW_NAME;
    }

    private void addUserAttributes(Model model, Principal principal) {
        if (principal != null) {
            User user = userService.getUserByName(principal.getName());
            model.addAttribute(user);
            userLevelService.getDiscountPercentage(user.getLevel())
                    .ifPresent(discount -> model.addAttribute("levelDiscount", discount));
        }
    }
}
