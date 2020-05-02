package com.es.core.dao;

import com.es.core.model.discount.Discount;

public interface DiscountDao {
    Discount get(Long id);
    void save(Discount discount);
}
