package com.es.phoneshop.web.controller.pages.admin;

import com.es.core.model.phone.Phone;
import com.es.core.service.product.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/products")
public class AdminProductsPageController {
    private static final int CURRENT_PAGE_NUM = 1;
    private static final int PAGE_SIZE = 10;
    private static final String PHONE_PAGE_ATTRIBUTE = "phonePage";
    private static final String VIEW_NAME = "admin/products";

    @Value("${phones.defaultSortName}")
    private String defaultPhonesSortName;

    @Value("${phones.defaultSortOrder}")
    private String defaultPhonesSortOrder;

    @Autowired
    private PhoneService phoneService;

    @GetMapping
    public String showProducts(Model model){
        Page<Phone> phonePage = phoneService.getPage(CURRENT_PAGE_NUM - 1, PAGE_SIZE, defaultPhonesSortName, defaultPhonesSortOrder);
        model.addAttribute(PHONE_PAGE_ATTRIBUTE, phonePage);
        return VIEW_NAME;
    }
}
