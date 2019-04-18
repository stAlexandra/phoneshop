package com.es.phoneshop.web.controller.pages.admin;

import com.es.core.model.order.Order;
import com.es.core.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

@Controller
@RequestMapping("/admin/orders")
public class OrdersPageController {
    private static final String VIEW_NAME = "admin/orders";
    private static final String ORDERS_ATTRIBUTE = "orderList";
    private static final String DATE_FORMATTER_ATTRIBUTE = "dateFormatter";

    private final OrderService orderService;

    @Autowired
    public OrdersPageController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String showOrders(Model model){
        List<Order> orderList = orderService.getAllOrdersSortedByDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        model.addAttribute(DATE_FORMATTER_ATTRIBUTE, formatter);
        model.addAttribute(ORDERS_ATTRIBUTE, orderList);
        return VIEW_NAME;
    }
}
