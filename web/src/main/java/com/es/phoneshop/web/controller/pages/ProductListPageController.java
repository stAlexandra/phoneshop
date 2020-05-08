package com.es.phoneshop.web.controller.pages;

import com.es.core.model.phone.Phone;
import com.es.core.service.checkout.CartService;
import com.es.core.service.product.PhoneService;
import com.es.core.util.PageIndexUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import st.alexandra.facades.UserFacade;

import javax.annotation.Resource;
import java.security.Principal;

@Controller
@RequestMapping("/productList")
public class ProductListPageController {
    private static final String DEFAULT_CURRENT_PAGE = "1";
    private static final String VIEW_NAME = "productList";

    @Value("${plp.defaultPageSize}")
    private int defaultPageSize;
    @Value("${plp.defaultNumPageIndexes}")
    private int defaultNumPageIndexes;

    @Resource
    private PhoneService phoneService;
    @Resource
    private CartService cartService;
    @Resource
    private UserFacade userFacade;

    @GetMapping
    public String showProductListPage(Model model,
                                      @RequestParam(name = "page", defaultValue = DEFAULT_CURRENT_PAGE) Integer currentPageNum,
                                      @RequestParam(name = "sort", defaultValue = "") String sortName,
                                      @RequestParam(name = "order", defaultValue = "") String sortOrder,
                                      Principal principal) {
        Page<Phone> phonePage = phoneService.getPage(currentPageNum - 1, defaultPageSize, sortName, sortOrder);
        int totalPages = phonePage.getTotalPages();

        addPageInfoAttributes(model, phonePage, currentPageNum, totalPages);
        model.addAttribute("user", userFacade.getUserData(principal));
        model.addAttribute("cart", cartService.getCart());
        return VIEW_NAME;
    }

    @GetMapping(params = {"query"})
    public String showProductListPage(Model model,
                                      @RequestParam(name = "page", defaultValue = DEFAULT_CURRENT_PAGE) Integer currentPageNum,
                                      @RequestParam(name = "query") String query,
                                      @RequestParam(name = "sort", defaultValue = "") String sortName,
                                      @RequestParam(name = "order", defaultValue = "") String sortOrder,
                                      Principal principal) {
        Page<Phone> phonePage = phoneService.getPage(currentPageNum - 1, defaultPageSize, query, sortName, sortOrder);
        int totalPages = phonePage.getTotalPages();

        addPageInfoAttributes(model, phonePage, currentPageNum, totalPages);
        model.addAttribute("user", userFacade.getUserData(principal));
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
