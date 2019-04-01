package com.es.phoneshop.web.controller.pages;

import com.es.core.model.phone.Phone;
import com.es.core.service.CartService;
import com.es.core.service.PhoneService;
import com.es.core.util.PageIndexUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/productList")
public class ProductListPageController {
    private static final String DEFAULT_CURRENT_PAGE = "1";
    private static final String VIEW_NAME = "productList";

    private final int defaultPageSize;
    private final int defaultNumPageIndexes;

    private PhoneService phoneService;
    private CartService cartService;

    @Autowired
    public ProductListPageController(PhoneService phoneService, CartService cartService,
                                     @Value("${plp.defaultPageSize}") int pageSize,
                                     @Value("${plp.defaultNumPageIndexes}") int numPageIndexes) {
        this.phoneService = phoneService;
        this.cartService = cartService;
        this.defaultPageSize = pageSize;
        this.defaultNumPageIndexes = numPageIndexes;
    }

    @GetMapping
    public String showProductListPage(Model model,
                                      @RequestParam(name = "page", defaultValue = DEFAULT_CURRENT_PAGE) Integer currentPageNum,
                                      @RequestParam(name = "sort", defaultValue = "") String sortName,
                                      @RequestParam(name = "order", defaultValue = "") String sortOrder) {
        Page<Phone> phonePage = phoneService.getPage(currentPageNum - 1, defaultPageSize, sortName, sortOrder);
        int totalPages = phonePage.getTotalPages();

        addPageInfoAttributes(model, phonePage, currentPageNum, totalPages);
        model.addAttribute("cart", cartService.getCart());
        return VIEW_NAME;
    }

    @GetMapping(params = {"query"})
    public String showProductListPage(Model model,
                                      @RequestParam(name = "page", defaultValue = DEFAULT_CURRENT_PAGE) Integer currentPageNum,
                                      @RequestParam(name = "query") String query,
                                      @RequestParam(name = "sort", defaultValue = "") String sortName,
                                      @RequestParam(name = "order", defaultValue = "") String sortOrder) {
        Page<Phone> phonePage = phoneService.getPage(currentPageNum - 1, defaultPageSize, query, sortName, sortOrder);
        int totalPages = phonePage.getTotalPages();

        addPageInfoAttributes(model, phonePage, currentPageNum, totalPages);
        model.addAttribute("cart", cartService.getCart());
        return VIEW_NAME;
    }

    private void addPageInfoAttributes(Model model, Page<Phone> phonePage, int current, int total) {
        model.addAttribute("phonePage", phonePage);
        model.addAttribute("beginPage", PageIndexUtil.getBegin(current, Math.min(defaultNumPageIndexes, total), total));
        model.addAttribute("endPage", PageIndexUtil.getEnd(current, Math.min(defaultNumPageIndexes, total), total));
        model.addAttribute("lastPage", total);
    }
}
