package com.es.phoneshop.web.controller.pages;

import com.es.core.model.user.User;
import com.es.core.service.UserService;
import com.es.core.service.businesslogic.UserLevelDiscountService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class UserPageController {
    private final UserService userService;
    private final UserLevelDiscountService userLevelDiscountService;

    private static final String VIEW_NAME = "userProfile";

    public UserPageController(UserService userService, UserLevelDiscountService userLevelDiscountService) {
        this.userService = userService;
        this.userLevelDiscountService = userLevelDiscountService;
    }

    @GetMapping("/my-profile")
    public ModelAndView getMyProfilePage(Principal principal){
        User user = userService.getUserByName(principal.getName());

        ModelAndView modelAndView = new ModelAndView(VIEW_NAME);
        modelAndView.addObject("user", user);

        if (user != null) {
            Double levelDiscount = userLevelDiscountService.getDiscountPercentage(user.getLevel()).orElse(Double.MIN_VALUE);
            modelAndView.addObject("levelDiscount", levelDiscount);
        }
        return modelAndView;
    }
}
