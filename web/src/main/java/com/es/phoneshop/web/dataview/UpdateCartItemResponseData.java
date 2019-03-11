package com.es.phoneshop.web.dataview;

import org.springframework.validation.FieldError;

import java.math.BigDecimal;

public class UpdateCartItemResponseData {
    private int numItems;
    private BigDecimal totalPrice;
    private FieldError quantityError;

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

    public FieldError getQuantityError() {
        return quantityError;
    }

    public void setQuantityError(FieldError quantityError) {
        this.quantityError = quantityError;
    }
}
