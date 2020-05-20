package com.es.phoneshop.web.controller.pages.admin;

import com.es.core.exception.ProductsNotFoundException;
import com.es.core.model.product.Book;
import com.es.core.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import st.alexandra.facades.dto.UpdatePhoneData;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin/products")
public class AdminProductDetailsPageController {
    private static final String VIEW_NAME = "admin/productDetails";
    private static final String PRODUCT_NOT_FOUND_VIEW = "productNotFound";
    private static final String PRODUCT = "phone";
    private static final String UPDATE_PHONE_DATA_ATTRIBUTE = "updatePhoneData";

    @Autowired
    private ProductService productService;

    @ExceptionHandler(ProductsNotFoundException.class)
    public String handlePhonesNotFoundException(ProductsNotFoundException e) {
        return PRODUCT_NOT_FOUND_VIEW;
    }

    @GetMapping(path = "/{id}")
    public String showProductDetails(@PathVariable("id") Long productId, Model model) {
        Book book = productService.get(productId);
        model.addAttribute(PRODUCT, book);
        UpdatePhoneData updatePhoneData = new UpdatePhoneData();
//        updatePhoneData.setWidthMm(phone.getWidthMm());
//        updatePhoneData.setLengthMm(phone.getLengthMm());

        model.addAttribute(UPDATE_PHONE_DATA_ATTRIBUTE, updatePhoneData);
        return VIEW_NAME;
    }

    @PutMapping(path = "/{id}")
    public ModelAndView editProduct(@PathVariable("id") Long phoneId, @Valid @ModelAttribute UpdatePhoneData updatePhoneData, BindingResult bindingResult, ModelMap modelMap){
        if(bindingResult.hasErrors()){
            modelMap.addAttribute(PRODUCT, productService.get(phoneId));
            return new ModelAndView(VIEW_NAME, modelMap, HttpStatus.BAD_REQUEST);
        }

        //service
        return new ModelAndView("redirect:/admin/products/" + phoneId);
    }
}