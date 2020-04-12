package com.es.core.dao;

import com.es.core.model.discount.Discount;

public interface UserLevelToDiscountDao {
    Discount getDiscountByUserLevel(Integer userLevel);
}
