package com.es.phoneshop.web.controller.pages.admin;

import com.es.core.model.order.OrderStatus;
import com.es.core.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/orders/{id}")
public class OrderDetailsPageController {
    private static final String VIEW_NAME = "admin/orderDetails";
    private static final String ORDER_ATTRIBUTE = "order";

    private final OrderService orderService;

    @Autowired
    public OrderDetailsPageController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String showOrder(@PathVariable Long id, Model model) {
        model.addAttribute(ORDER_ATTRIBUTE, orderService.getOrderById(id));
        return VIEW_NAME;
    }

    @PostMapping
    public String updateStatus(@PathVariable Long id, @RequestParam OrderStatus orderStatus){
        orderService.updateOrderStatus(id, orderStatus);
        return "redirect:/admin/orders/{id}";
    }
}
