package com.es.phoneshop.web.dataview;

import java.util.ArrayList;
import java.util.List;

public class UpdateCartRequestData {
    private List<UpdateCartItemRequestData> cartItemDataList;

    public UpdateCartRequestData() {
        this.cartItemDataList = new ArrayList<>();
    }

    public List<UpdateCartItemRequestData> getCartItemDataList() {
        return cartItemDataList;
    }

    public void setCartItemDataList(List<UpdateCartItemRequestData> cartItemDataList) {
        this.cartItemDataList = cartItemDataList;
    }
}
