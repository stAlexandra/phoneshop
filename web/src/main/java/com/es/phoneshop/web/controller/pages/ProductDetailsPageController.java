package com.es.phoneshop.web.controller.pages;

import com.es.core.dao.PhoneDao;
import com.es.core.exception.PhonesNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import st.alexandra.facades.CartFacade;
import st.alexandra.facades.UserFacade;

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
    private CartFacade cartFacade;
    @Resource
    private UserFacade userFacade;

    @ExceptionHandler(PhonesNotFoundException.class)
    public String handlePhonesNotFoundException(PhonesNotFoundException e) {
        return PHONE_NOT_FOUND_VIEW_NAME;
    }

    @GetMapping(path = "/{id}")
    public String showProductDetails(@PathVariable("id") Long phoneId, Model model, Principal principal) {
        model.addAttribute("user", userFacade.getUserData(principal));
        model.addAttribute("phone", phoneDao.get(phoneId));
        model.addAttribute("cart", cartFacade.getCart());
        return VIEW_NAME;
    }

}
