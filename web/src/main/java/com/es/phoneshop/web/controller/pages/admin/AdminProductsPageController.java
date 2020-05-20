package com.es.phoneshop.web.controller.pages.admin;

import com.es.core.model.product.Book;
import com.es.core.service.product.ProductService;
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
    private static final String PRODUCT_PAGE_ATTRIBUTE = "productsPage";
    private static final String VIEW_NAME = "admin/products";

    @Value("${phones.defaultSortName}")
    private String defaultSortName;

    @Value("${phones.defaultSortOrder}")
    private String defaultSortOrder;

    @Autowired
    private ProductService productService;

    @GetMapping
    public String showProducts(Model model){
        Page<Book> productPage = productService.getPage(CURRENT_PAGE_NUM - 1, PAGE_SIZE, defaultSortName, defaultSortOrder);
        model.addAttribute(PRODUCT_PAGE_ATTRIBUTE, productPage);
        return VIEW_NAME;
    }
}
