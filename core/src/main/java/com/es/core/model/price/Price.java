package com.es.core.model.price;

import java.math.BigDecimal;

public class Price {
    private String currencyName;
    private BigDecimal value;

    public Price() {
    }

    public Price(String currencyName, BigDecimal value) {
        this.currencyName = currencyName;
        this.value = value;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
