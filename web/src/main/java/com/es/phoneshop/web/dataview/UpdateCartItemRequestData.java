package com.es.phoneshop.web.dataview;


import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class UpdateCartItemRequestData {
    @NotNull
    private Long phoneId;
    @NotNull @DecimalMin(value = "1", message = "Should be greater than or equal to 1")
    private Long quantity;

    public Long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Long phoneId) {
        this.phoneId = phoneId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
