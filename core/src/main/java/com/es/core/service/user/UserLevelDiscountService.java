package com.es.core.service.user;

import com.es.core.model.user.User;

import java.util.Optional;

public interface UserLevelDiscountService {
    Optional<Double> getDiscountPercentage(Integer userLevel);
    boolean activateLevelDiscountForUser(User user);
}
