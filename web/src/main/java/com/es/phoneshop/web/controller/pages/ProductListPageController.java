package com.es.phoneshop.web.controller.pages;

import com.es.core.model.phone.Phone;
import com.es.core.service.PhoneService;
import com.es.core.util.PageIndexUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/productList")
public class ProductListPageController {
    private static final int DEFAULT_CURRENT_PAGE = 1;
    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final int DEFAULT_NUM_PAGE_INDEXES = 9;

    private PhoneService phoneService;

    @Autowired
    public ProductListPageController(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showProductListPage(Model model, @RequestParam(name = "page", required = false) Integer page,
                                      @RequestParam(name = "size", required = false) Integer pageSize) {
        if (page == null) {
            page = DEFAULT_CURRENT_PAGE;
        }
        if (pageSize == null) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        Page<Phone> phonePage = phoneService.findPaginated(PageRequest.of(page - 1, pageSize));
        int totalPages = phonePage.getTotalPages();

        model.addAttribute("phonePage", phonePage);
        model.addAttribute("beginPage", PageIndexUtil.getBegin(page, DEFAULT_NUM_PAGE_INDEXES, totalPages));
        model.addAttribute("endPage", PageIndexUtil.getEnd(page, DEFAULT_NUM_PAGE_INDEXES, totalPages));
        return "productList";
    }
}
