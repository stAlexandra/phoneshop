package com.es.phoneshop.web.dataview;

import com.es.core.model.cart.Cart;
import com.es.core.model.cart.CartItem;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


public class UpdateCartRequestData {
    @Valid
    private List<UpdateCartItemRequestData> cartItemDataList;

    public UpdateCartRequestData() {
        this.cartItemDataList = new ArrayList<>();
    }

    public UpdateCartRequestData(Cart cart){
        this.cartItemDataList = new ArrayList<>();
        for(CartItem cartItem : cart.getItems()){
            UpdateCartItemRequestData cartItemRequestData = new UpdateCartItemRequestData(cartItem.getPhone().getId(), cartItem.getQuantity());
            this.cartItemDataList.add(cartItemRequestData);
        }
    }

    public List<UpdateCartItemRequestData> getCartItemDataList() {
        return cartItemDataList;
    }

    public void setCartItemDataList(List<UpdateCartItemRequestData> cartItemDataList) {
        this.cartItemDataList = cartItemDataList;
    }
}
