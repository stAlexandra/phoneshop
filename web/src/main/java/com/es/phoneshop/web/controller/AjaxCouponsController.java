package com.es.phoneshop.web.controller;

import org.springframework.web.bind.annotation.*;
import st.alexandra.facades.PromotionsFacade;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RestController
@RequestMapping("**/activateCoupon")
public class AjaxCouponsController {

    @Resource
    private PromotionsFacade promotionsFacade;

    @PostMapping
    public void activateCoupon(@RequestParam(name = "couponCode") String couponCode, Principal principal,
                                 HttpServletResponse response) {
        boolean activated = promotionsFacade.activateCartCoupon(principal.getName(), couponCode);
        if (!activated) response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        else response.setStatus(HttpServletResponse.SC_OK);
    }
}
