package com.es.core.model.discount;

import java.math.BigDecimal;

public class Discount {
    private Long id;
    private BigDecimal value = BigDecimal.ZERO;
    private DiscountValueType valueType;
    private DiscountApplicableFor applicableFor;
    private boolean enabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public DiscountValueType getValueType() {
        return valueType;
    }

    public void setValueType(DiscountValueType valueType) {
        this.valueType = valueType;
    }

    public DiscountApplicableFor getApplicableFor() {
        return applicableFor;
    }

    public void setApplicableFor(DiscountApplicableFor applicableFor) {
        this.applicableFor = applicableFor;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
