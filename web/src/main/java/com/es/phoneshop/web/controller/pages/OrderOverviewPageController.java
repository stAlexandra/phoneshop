package com.es.phoneshop.web.controller.pages;

import com.es.core.model.order.Order;
import com.es.core.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/orderOverview/*")
public class OrderOverviewPageController {
    private OrderService orderService;

    @Autowired
    public OrderOverviewPageController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{secureId}")
    public String showOrderOverview(@PathVariable("secureId") String secureId, Model model) {
        Order order = orderService.getOrderBySecureId(secureId);
        model.addAttribute("order", order);
        return "orderOverview";
    }
}
