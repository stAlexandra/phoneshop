package com.es.phoneshop.web.controller;

import com.es.core.model.cart.Cart;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import st.alexandra.facades.CartFacade;
import st.alexandra.facades.dto.UpdateCartItemRequestData;
import st.alexandra.facades.dto.UpdateCartItemResponseData;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("**/ajaxCart")
public class AjaxCartController {
    @Resource
    private CartFacade cartFacade;

    @PostMapping
    public ResponseEntity<UpdateCartItemResponseData> addPhone(@Valid UpdateCartItemRequestData requestData, BindingResult bindingResult) {
        UpdateCartItemResponseData responseData = new UpdateCartItemResponseData();
        if (bindingResult.hasErrors()) {
            responseData.setQuantityErrorMessage(bindingResult.getFieldError("quantity").getDefaultMessage());
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        } else {
            cartFacade.addPhone(requestData.getPhoneId(), requestData.getQuantity());
            Cart cart = cartFacade.getCart();

            responseData.setNumItems(cart.getItems().size());
            responseData.setTotalPrice(cart.getTotalPrice());
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        }
    }
}
