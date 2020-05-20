package com.es.phoneshop.web.controller.pages;

import com.es.core.exception.ProductsNotFoundException;
import com.es.core.service.product.ProductService;
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
    private ProductService productService;
    @Resource
    private CartFacade cartFacade;
    @Resource
    private UserFacade userFacade;

    @ExceptionHandler(ProductsNotFoundException.class)
    public String handlePhonesNotFoundException(ProductsNotFoundException e) {
        return PHONE_NOT_FOUND_VIEW_NAME;
    }

    @GetMapping(path = "/{id}")
    public String showProductDetails(@PathVariable("id") Long id, Model model, Principal principal) {
        model.addAttribute("user", userFacade.getUserData(principal));
        model.addAttribute("book", productService.get(id));
        model.addAttribute("cart", cartFacade.getCart());
        return VIEW_NAME;
    }

}
