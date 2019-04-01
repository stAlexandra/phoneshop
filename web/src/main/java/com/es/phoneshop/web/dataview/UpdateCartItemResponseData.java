package com.es.phoneshop.web.dataview;

import java.math.BigDecimal;

public class UpdateCartItemResponseData {
    private int numItems;
    private BigDecimal totalPrice = BigDecimal.ZERO;
    private String quantityErrorMessage;

    public int getNumItems() {
        return numItems;
    }

    public void setNumItems(int numItems) {
        this.numItems = numItems;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getQuantityErrorMessage() {
        return quantityErrorMessage;
    }

    public void setQuantityErrorMessage(String quantityErrorMessage) {
        this.quantityErrorMessage = quantityErrorMessage;
    }
}
