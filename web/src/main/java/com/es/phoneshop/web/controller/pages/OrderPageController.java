package com.es.phoneshop.web.controller.pages;

import com.es.core.exception.OutOfStockException;
import com.es.core.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/order")
public class OrderPageController {
    @Resource
    private OrderService orderService;

    @GetMapping
    public void getOrder() throws OutOfStockException {
        orderService.createOrder(null);
    }

    @PostMapping
    public void placeOrder() throws OutOfStockException {
        orderService.placeOrder(null);
    }
}
