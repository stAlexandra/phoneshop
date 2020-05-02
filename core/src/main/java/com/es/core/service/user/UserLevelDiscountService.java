package com.es.core.service.user;

import java.util.Optional;

public interface UserLevelDiscountService {
    Optional<Double> getDiscountPercentage(Integer userLevel);
}
