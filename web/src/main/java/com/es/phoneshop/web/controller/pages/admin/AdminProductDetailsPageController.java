package com.es.phoneshop.web.controller.pages.admin;

import com.es.core.exception.PhonesNotFoundException;
import com.es.core.model.phone.Phone;
import com.es.core.service.PhoneService;
import com.es.core.service.PriceService;
import st.alexandra.facades.dto.UpdatePhoneData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin/products")
public class AdminProductDetailsPageController {
    private static final String VIEW_NAME = "admin/productDetails";
    private static final String PHONE_NOT_FOUND_VIEW_NAME = "phoneNotFound";
    private static final String PHONE_ATTRIBUTE = "phone";
    private static final String UPDATE_PHONE_DATA_ATTRIBUTE = "updatePhoneData";

    @Autowired
    private PhoneService phoneService;

    @Autowired
    private PriceService priceService;

    @ExceptionHandler(PhonesNotFoundException.class)
    public String handlePhonesNotFoundException(PhonesNotFoundException e) {
        return PHONE_NOT_FOUND_VIEW_NAME;
    }

    @GetMapping(path = "/{id}")
    public String showProductDetails(@PathVariable("id") Long phoneId, Model model) {
        Phone phone = phoneService.getPhone(phoneId);
        model.addAttribute(PHONE_ATTRIBUTE, phone);

        UpdatePhoneData updatePhoneData = new UpdatePhoneData();
        updatePhoneData.setWidthMm(phone.getWidthMm());
        updatePhoneData.setLengthMm(phone.getLengthMm());

        model.addAttribute(UPDATE_PHONE_DATA_ATTRIBUTE, updatePhoneData);
        return VIEW_NAME;
    }

    @PutMapping(path = "/{id}")
    public ModelAndView editProduct(@PathVariable("id") Long phoneId, @Valid @ModelAttribute UpdatePhoneData updatePhoneData, BindingResult bindingResult, ModelMap modelMap){
        if(bindingResult.hasErrors()){
            modelMap.addAttribute(PHONE_ATTRIBUTE, phoneService.getPhone(phoneId));
            return new ModelAndView(VIEW_NAME, modelMap, HttpStatus.BAD_REQUEST);
        }

        //service
        return new ModelAndView("redirect:/admin/products/" + phoneId);
    }
}