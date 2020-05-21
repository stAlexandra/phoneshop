package com.es.phoneshop.web.controller.pages;

import com.es.core.model.order.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import st.alexandra.facades.CartFacade;
import st.alexandra.facades.OrderFacade;
import st.alexandra.facades.UserFacade;
import st.alexandra.facades.dto.OrderData;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/order")
public class OrderPageController {
    private static final String VIEW_NAME = "order";
    private static final String ORDER_OVERVIEW = "orderOverview/";
    private static final String ORDER = "order/";
    private static final String REDIRECT_PREFIX = "redirect:/";

    private static final String CART_ATTRIBUTE = "cart";
    private static final String ORDER_DATA_ATTRIBUTE = "orderData";
    private static final String USER_DATA_ATTRIBUTE = "user";

    @Resource
    private OrderFacade orderFacade;
    @Resource
    private CartFacade cartFacade;
    @Resource
    private Validator orderDataValidator;
    @Resource
    private UserFacade userFacade;

    @InitBinder("orderData")
    private void initBinder(WebDataBinder binder) {
        binder.addValidators(orderDataValidator);
    }

    @GetMapping
    public String getOrder(Model model, Principal principal) {
        model.addAttribute(CART_ATTRIBUTE, cartFacade.getCart());
        model.addAttribute(ORDER_DATA_ATTRIBUTE, new OrderData());
        model.addAttribute(USER_DATA_ATTRIBUTE, userFacade.getUserData(principal));
        return VIEW_NAME;
    }

    @PostMapping
    public String placeOrder(@Valid @ModelAttribute OrderData orderData, BindingResult bindingResult, Model model,
                             Principal principal) {
        if(bindingResult.hasErrors()) {
            model.addAttribute(CART_ATTRIBUTE, cartFacade.getCart());
            return VIEW_NAME;
        }
        Order order = orderFacade.createOrder(cartFacade.getCart(), orderData);
        orderFacade.placeOrder(order, principal.getName());
        return REDIRECT_PREFIX + ORDER_OVERVIEW + order.getSecureId();
    }

}
