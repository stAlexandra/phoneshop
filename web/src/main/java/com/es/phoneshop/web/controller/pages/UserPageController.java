package com.es.phoneshop.web.controller.pages;

import com.es.core.model.user.User;
import com.es.core.service.user.UserLevelService;
import com.es.core.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class UserPageController {
    private final UserService userService;
    private final UserLevelService userLevelService;

    private static final String VIEW_NAME = "userProfile";

    public UserPageController(UserService userService, UserLevelService userLevelService) {
        this.userService = userService;
        this.userLevelService = userLevelService;
    }

    @GetMapping("/my-profile")
    public ModelAndView getMyProfilePage(Principal principal){
        ModelAndView modelAndView = new ModelAndView(VIEW_NAME);
        User user = userService.getUserByName(principal.getName());
        modelAndView.addObject("user", user);
        if (user != null) {
            Double levelDiscount = userLevelService.getDiscountPercentage(user.getLevel()).orElse(Double.MIN_VALUE);
            modelAndView.addObject("levelDiscount", levelDiscount);
        }
        return modelAndView;
    }
}
