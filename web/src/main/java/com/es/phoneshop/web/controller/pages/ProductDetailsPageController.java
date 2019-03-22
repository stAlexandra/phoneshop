package com.es.phoneshop.web.controller.pages;

import com.es.core.dao.PhoneDao;
import com.es.core.exception.PhonesNotFoundException;
import com.es.core.service.CartService;
import com.es.phoneshop.web.util.MiniCartModelAttributeSetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ProductDetailsPageController {
    private PhoneDao phoneDao;
    private CartService cartService;

    @Autowired
    public ProductDetailsPageController(PhoneDao phoneDao, CartService cartService) {
        this.phoneDao = phoneDao;
        this.cartService = cartService;
    }

    @RequestMapping(value = "/productDetails/{id:[\\d]+}", method = RequestMethod.GET)
    public String showProductDetails(@PathVariable("id") Long phoneId, Model model){
        try {
            model.addAttribute("phone", phoneDao.get(phoneId));
            MiniCartModelAttributeSetter.addMiniCartInfoAttributes(model, cartService.getCart());
        } catch (PhonesNotFoundException e){
            //TODO 404
        }
        return "productDetails";
    }
}
