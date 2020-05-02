package com.es.core.dao;

import com.es.core.model.discount.Discount;

import java.util.Collection;

public interface UserDiscountsDao {
    Collection<Discount> getByUserName(String username);
    void save(String username, Discount discount);
//    void save(String username, Collection<Discount> discounts);
}
