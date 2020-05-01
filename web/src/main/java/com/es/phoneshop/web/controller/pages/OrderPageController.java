package com.es.phoneshop.web.controller.pages;

import com.es.core.model.order.CustomerInfo;
import com.es.core.model.order.Order;
import com.es.core.service.CartService;
import com.es.core.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import st.alexandra.facades.PromotionsFacade;
import st.alexandra.facades.dto.OrderData;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@RequestMapping("/order")
public class OrderPageController {
    private static final String VIEW_NAME = "order";
    private static final String ORDER_OVERVIEW = "orderOverview/";
    private static final String ORDER = "order/";
    private static final String REDIRECT_PREFIX = "redirect:/";

    private static final String CART_ATTRIBUTE = "cart";
    private static final String ORDER_DATA_ATTRIBUTE = "orderData";

    private final OrderService orderService;
    private final CartService cartService;
    private final Validator orderDataValidator;

    @Autowired
    public OrderPageController(OrderService orderService, CartService cartService, Validator orderDataValidator) {
        this.orderService = orderService;
        this.cartService = cartService;
        this.orderDataValidator = orderDataValidator;
    }

    @InitBinder("orderData")
    private void initBinder(WebDataBinder binder) {
        binder.addValidators(orderDataValidator);
    }

    @GetMapping
    public String getOrder(Model model) {
        model.addAttribute(CART_ATTRIBUTE, cartService.getCart());
        model.addAttribute(ORDER_DATA_ATTRIBUTE, new OrderData());
        return VIEW_NAME;
    }

    @PostMapping
    public String placeOrder(@Valid @ModelAttribute OrderData orderData, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            model.addAttribute(CART_ATTRIBUTE, cartService.getCart());
            return VIEW_NAME;
        }
        CustomerInfo customerInfo = new CustomerInfo(orderData.getFirstName(), orderData.getLastName(),
                orderData.getDeliveryAddress(), orderData.getContactPhoneNo());
        Order order = orderService.createOrder(cartService.getCart(), customerInfo);
        orderService.placeOrder(order);
        return REDIRECT_PREFIX + ORDER_OVERVIEW + order.getSecureId();
    }

}
