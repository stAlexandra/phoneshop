package com.es.phoneshop.web.controller.pages;

import com.es.core.model.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import st.alexandra.facades.OrderFacade;

@Controller
@RequestMapping("/orderOverview/*")
public class OrderOverviewPageController {
    private static final String VIEW_NAME = "orderOverview";
    private static final String ORDER_ATTRIBUTE = "order";
    private OrderFacade orderFacade;

    @Autowired
    public OrderOverviewPageController(OrderFacade orderFacade) {
        this.orderFacade = orderFacade;
    }

    @GetMapping("/{secureId}")
    public String showOrderOverview(@PathVariable String secureId, Model model) {
        Order order = orderFacade.getOrderBySecureId(secureId);
        model.addAttribute(ORDER_ATTRIBUTE, order);
        return VIEW_NAME;
    }
}
