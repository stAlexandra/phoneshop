package com.es.phoneshop.web.controller.pages.admin;

import com.es.core.model.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import st.alexandra.facades.OrderFacade;

import java.util.List;

@Controller
@RequestMapping("/admin/orders")
public class OrdersPageController {
    private static final String VIEW_NAME = "admin/orders";
    private static final String ORDERS_ATTRIBUTE = "orderList";

    private final OrderFacade orderFacade;

    @Autowired
    public OrdersPageController(OrderFacade orderFacade) {
        this.orderFacade = orderFacade;
    }

    @GetMapping
    public String showOrders(Model model){
        List<Order> orderList = orderFacade.getAllOrdersSortedByDate();
        model.addAttribute(ORDERS_ATTRIBUTE, orderList);
        return VIEW_NAME;
    }
}
