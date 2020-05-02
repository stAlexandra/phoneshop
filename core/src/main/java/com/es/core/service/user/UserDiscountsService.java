package com.es.core.service.user;

import com.es.core.model.discount.Discount;
import com.es.core.model.user.User;

public interface UserDiscountsService {
    void addDiscount(User user, Discount discount);
    void removeDiscount(User user, Discount discount);
    boolean hasDiscount(User user, Discount discount);
}
