package com.es.phoneshop.web.controller.pages;

import com.es.core.exception.OutOfStockException;
import com.es.core.model.order.CustomerInfo;
import com.es.core.model.order.Order;
import com.es.core.service.CartService;
import com.es.core.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/order")
public class OrderPageController {
    private static final String VIEW_NAME = "order";
    private static final String ORDER_OVERVIEW = "orderOverview/";
    private static final String CART_ATTRIBUTE = "cart";
    private static final String CUSTOMER_INFO_ATTRIBUTE = "customerInfo";
    private static final String OUT_OF_STOCK_PHONES_ATTRIBUTE = "outOfStockPhones";
    private final OrderService orderService;
    private final CartService cartService;

    @Autowired
    public OrderPageController(OrderService orderService, CartService cartService) {
        this.orderService = orderService;
        this.cartService = cartService;
    }

    @GetMapping
    public String getOrder(@RequestParam(value = OUT_OF_STOCK_PHONES_ATTRIBUTE, required = false) String outOfStockPhones,
                           Model model) {
        model.addAttribute(CART_ATTRIBUTE, cartService.getCart());
        model.addAttribute(OUT_OF_STOCK_PHONES_ATTRIBUTE, outOfStockPhones);
        model.addAttribute(CUSTOMER_INFO_ATTRIBUTE, new CustomerInfo());
        return VIEW_NAME;
    }

    @PostMapping
    public String placeOrder(@Valid @ModelAttribute CustomerInfo customerInfo, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(CART_ATTRIBUTE, cartService.getCart());
            return VIEW_NAME;
        }
        try {
            Order order = orderService.createOrder(cartService.getCart(), customerInfo);
            orderService.placeOrder(order);
            return "redirect:/" + ORDER_OVERVIEW + order.getSecureId();
        } catch (OutOfStockException e) {
            model.addAttribute(OUT_OF_STOCK_PHONES_ATTRIBUTE, e.getMessage());
            return "redirect:/" + VIEW_NAME;
        }
    }
}
