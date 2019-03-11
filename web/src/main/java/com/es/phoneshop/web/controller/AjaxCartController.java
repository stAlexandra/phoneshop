package com.es.phoneshop.web.controller;

import com.es.core.model.cart.Cart;
import com.es.core.service.CartService;
import com.es.phoneshop.web.dataview.UpdateCartItemRequestData;
import com.es.phoneshop.web.dataview.UpdateCartItemResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/ajaxCart")
public class AjaxCartController {
    @Resource
    private CartService cartService;

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UpdateCartItemResponseData addPhone(@Valid @RequestBody UpdateCartItemRequestData requestData, BindingResult bindingResult) {
        UpdateCartItemResponseData responseData = new UpdateCartItemResponseData();
        if (bindingResult.hasErrors()) {
            responseData.setQuantityError(bindingResult.getFieldError("quantity"));
        } else {
            cartService.addPhone(requestData.getPhoneId(), requestData.getQuantity());
            Cart cart = cartService.getCart();

            responseData.setNumItems(cart.getItems().size());
            responseData.setTotalPrice(cart.getTotalPrice());
        }
        return responseData;
    }
}
