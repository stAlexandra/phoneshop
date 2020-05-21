package com.es.core.service.user;

import com.es.core.model.user.User;

import java.util.Optional;

public interface UserLevelService {
    Optional<Double> getDiscountPercentage(Integer userLevel);
    boolean addLevelDiscountForUser(User user);
    void addXP(User user, Long xp);
}
