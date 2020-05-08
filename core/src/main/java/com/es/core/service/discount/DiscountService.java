package com.es.core.service.discount;

import com.es.core.model.discount.Discount;

import java.util.Collection;

public interface DiscountService {
    boolean applyDiscount(Discount discount);
    boolean applyDiscount(Collection<Discount> discounts);
}
