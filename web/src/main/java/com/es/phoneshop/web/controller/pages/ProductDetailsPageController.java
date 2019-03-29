package com.es.phoneshop.web.controller.pages;

import com.es.core.dao.PhoneDao;
import com.es.core.exception.PhonesNotFoundException;
import com.es.core.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/productDetails")
public class ProductDetailsPageController {
    private static final String VIEW_NAME = "productDetails";
    private static final String PHONE_NOT_FOUND_VIEW_NAME = "phoneNotFound";
    private PhoneDao phoneDao;
    private CartService cartService;

    @Autowired
    public ProductDetailsPageController(PhoneDao phoneDao, CartService cartService) {
        this.phoneDao = phoneDao;
        this.cartService = cartService;
    }

    @ExceptionHandler(PhonesNotFoundException.class)
    public ModelAndView handlePhonesNotFoundException(PhonesNotFoundException e) {
        ModelAndView modelAndView = new ModelAndView(PHONE_NOT_FOUND_VIEW_NAME);
        modelAndView.addObject("errorMsg", e.getMessage());
        return modelAndView;
    }

    @GetMapping(path = "/{id}")
    public String showProductDetails(@PathVariable("id") Long phoneId, Model model) {
        model.addAttribute("phone", phoneDao.get(phoneId));
        model.addAttribute("cart", cartService.getCart());
        return VIEW_NAME;
    }
}
