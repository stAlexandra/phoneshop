package com.es.core.dao;

import com.es.core.model.discount.Discount;

public interface UserLevelDiscountDao {
    Discount getByUserLevel(Integer userLevel);
}
