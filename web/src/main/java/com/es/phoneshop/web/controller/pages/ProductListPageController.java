package com.es.phoneshop.web.controller.pages;

import com.es.core.model.phone.Phone;
import com.es.core.service.PhoneService;
import com.es.core.util.PageIndexUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/productList")
public class ProductListPageController {
    private static final String DEFAULT_CURRENT_PAGE = "1";
    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final int DEFAULT_NUM_PAGE_INDEXES = 9;
    private static final String viewName = "productList";

    private PhoneService phoneService;

    @Autowired
    public ProductListPageController(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showProductListPage(Model model, @RequestParam(name = "page", defaultValue = DEFAULT_CURRENT_PAGE) Integer currentPageNum) {

        Page<Phone> phonePage = phoneService.getPage(currentPageNum - 1, DEFAULT_PAGE_SIZE);
        int totalPages = phonePage.getTotalPages();

        addPageInfoAttributes(model, phonePage, currentPageNum, totalPages);
        return viewName;
    }

    @RequestMapping(params = {"query"}, method = RequestMethod.GET)
    public String showProductListPage(Model model, @RequestParam(name = "page", defaultValue = DEFAULT_CURRENT_PAGE) Integer currentPageNum,
                                      @RequestParam(name = "query") String query) {
        Page<Phone> phonePage = phoneService.getPage(currentPageNum - 1, DEFAULT_PAGE_SIZE, query);
        int totalPages = phonePage.getTotalPages();

        addPageInfoAttributes(model, phonePage, currentPageNum, totalPages);
        return viewName;
    }

    @RequestMapping(params = { "sort", "order" }, method = RequestMethod.GET)
    public String showProductListPage(Model model, @RequestParam(name = "page", defaultValue = DEFAULT_CURRENT_PAGE) Integer currentPageNum,
                                      @RequestParam(name = "sort") String sortName, @RequestParam(name = "order") String sortOrder) {
        Page<Phone> phonePage = phoneService.getPage(currentPageNum - 1, DEFAULT_PAGE_SIZE, sortName, sortOrder);
        int totalPages = phonePage.getTotalPages();

        addPageInfoAttributes(model, phonePage, currentPageNum, totalPages);
        return viewName;
    }

    @RequestMapping(params = {"query", "sort", "order"}, method = RequestMethod.GET)
    public String showProductListPage(Model model, @RequestParam(name = "page", defaultValue = DEFAULT_CURRENT_PAGE) Integer currentPageNum,
                                      @RequestParam(name = "query") String query,
                                      @RequestParam(name = "sort") String sortName, @RequestParam(name = "order") String sortOrder) {
        Page<Phone> phonePage;
        if(query.isEmpty()){
            phonePage = phoneService.getPage(currentPageNum - 1, DEFAULT_PAGE_SIZE, sortName, sortOrder);
        } else {
            phonePage = phoneService.getPage(currentPageNum - 1, DEFAULT_PAGE_SIZE, query, sortName, sortOrder);
        }

        int totalPages = phonePage.getTotalPages();

        addPageInfoAttributes(model, phonePage, currentPageNum, totalPages);
        return viewName;
    }

    private void addPageInfoAttributes(Model model, Page<Phone> phonePage, int current, int total){
        model.addAttribute("phonePage", phonePage);
        model.addAttribute("beginPage", PageIndexUtil.getBegin(current, Math.min(DEFAULT_NUM_PAGE_INDEXES, total), total));
        model.addAttribute("endPage", PageIndexUtil.getEnd(current, Math.min(DEFAULT_NUM_PAGE_INDEXES, total), total));
    }
}
