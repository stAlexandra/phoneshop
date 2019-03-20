package com.es.phoneshop.web.dataview;

import javax.validation.constraints.*;

public class UpdateCartItemRequestData {
    @NotNull
    private Long phoneId;

    @NotNull
    @Min(value = 1L)
    @Max(value = 20L)
    private Long quantity;

    public Long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Long phoneId) {
        this.phoneId = phoneId;
    }

    public Long getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
