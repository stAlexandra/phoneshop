package com.es.phoneshop.web.controller.pages.admin;

import com.es.core.model.order.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import st.alexandra.facades.OrderFacade;

@Controller
@RequestMapping("/admin/orders/{id}")
public class OrderDetailsPageController {
    private static final String VIEW_NAME = "admin/orderDetails";
    private static final String ORDER_ATTRIBUTE = "order";

    private final OrderFacade orderFacade;

    @Autowired
    public OrderDetailsPageController(OrderFacade orderFacade) {
        this.orderFacade = orderFacade;
    }

    @GetMapping
    public String showOrder(@PathVariable Long id, Model model) {
        model.addAttribute(ORDER_ATTRIBUTE, orderFacade.getOrderById(id));
        return VIEW_NAME;
    }

    @PutMapping
    public String updateStatus(@PathVariable Long id, @RequestParam OrderStatus orderStatus){
        orderFacade.updateOrderStatus(id, orderStatus);
        return "redirect:/admin/orders/{id}";
    }
}
