package com.es.phoneshop.web.controller.pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import st.alexandra.facades.UserFacade;
import st.alexandra.facades.dto.UserData;

import java.security.Principal;

@Controller
public class UserPageController {
    private final UserFacade userFacade;

    private static final String VIEW_NAME = "userProfile";

    public UserPageController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @GetMapping("/my-profile")
    public ModelAndView getMyProfilePage(Principal principal){
        ModelAndView modelAndView = new ModelAndView(VIEW_NAME);
        UserData userData = userFacade.getUserData(principal);
        modelAndView.addObject("user", userData);
        return modelAndView;
    }
}
