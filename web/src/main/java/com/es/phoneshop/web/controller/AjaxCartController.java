package com.es.phoneshop.web.controller;

import com.es.core.model.cart.Cart;
import com.es.core.service.CartService;
import com.es.phoneshop.web.dataview.UpdateCartItemRequestData;
import com.es.phoneshop.web.dataview.UpdateCartItemResponseData;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "**/ajaxCart")
public class AjaxCartController {
    @Resource
    private CartService cartService;

    @ExceptionHandler(InvalidFormatException.class)
    @ResponseBody
    public UpdateCartItemResponseData handleInvalidFormatError(){
        UpdateCartItemResponseData responseData = new UpdateCartItemResponseData();
        FieldError quantityFieldError = new FieldError("updateCartItemRequestData", "quantity", "wrong format");
        responseData.setQuantityError(quantityFieldError);
        return responseData;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
