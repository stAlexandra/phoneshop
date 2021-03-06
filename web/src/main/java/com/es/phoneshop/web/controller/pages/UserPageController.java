package com.es.phoneshop.web.controller.pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class UserPageController {
    private static final String VIEW_NAME = "userProfile";

    @GetMapping("/my-profile")
    public ModelAndView getMyProfilePage(Principal principal){
        ModelAndView modelAndView = new ModelAndView(VIEW_NAME);
        modelAndView.addObject("userName", principal.getName());
        return modelAndView;
    }
}
