package com.es.phoneshop.web.controller;

import com.es.core.model.cart.Cart;
import com.es.core.service.CartService;
import com.es.phoneshop.web.dataview.UpdateCartItemRequestData;
import com.es.phoneshop.web.dataview.UpdateCartItemResponseData;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("**/ajaxCart")
public class AjaxCartController {
    @Resource
    private CartService cartService;

    @ExceptionHandler(InvalidFormatException.class)
    public UpdateCartItemResponseData handleInvalidFormatException(){
        UpdateCartItemResponseData responseData = new UpdateCartItemResponseData();
        responseData.setQuantityErrorMessage("wrong format");
        return responseData;
    }

    @PostMapping
    public ResponseEntity<UpdateCartItemResponseData> addPhone(@Valid UpdateCartItemRequestData requestData, BindingResult bindingResult) {
        UpdateCartItemResponseData responseData = new UpdateCartItemResponseData();
        if (bindingResult.hasErrors()) {
            responseData.setQuantityErrorMessage(bindingResult.getFieldError("quantity").getDefaultMessage());
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        } else {
            cartService.addPhone(requestData.getPhoneId(), requestData.getQuantity());
            Cart cart = cartService.getCart();

            responseData.setNumItems(cart.getItems().size());
            responseData.setTotalPrice(cart.getTotalPrice());
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        }
    }
}
