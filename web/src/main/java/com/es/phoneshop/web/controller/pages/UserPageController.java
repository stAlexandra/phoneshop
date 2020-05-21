package com.es.phoneshop.web.controller.pages;

import com.es.core.model.order.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import st.alexandra.facades.UserFacade;
import st.alexandra.facades.dto.UserData;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/my-profile")
public class UserPageController {
    private final UserFacade userFacade;

    private static final String VIEW_NAME = "userProfile";
    private static final String ORDERS_VIEW_NAME = "userOrders";

    public UserPageController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @GetMapping
    public ModelAndView getMyProfilePage(Principal principal){
        ModelAndView modelAndView = new ModelAndView(VIEW_NAME);
        UserData userData = userFacade.getUserData(principal);
        modelAndView.addObject("user", userData);
        return modelAndView;
    }

    @GetMapping("/orders")
    public String getMyOrders(Model model, Principal principal) {
        List<Order> orderList = userFacade.getUserOrders(principal.getName());
        model.addAttribute("orderList", orderList);
        return ORDERS_VIEW_NAME;
    }
}
