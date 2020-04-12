package com.es.core.service.businesslogic;

import java.util.Optional;

public interface UserLevelDiscountService {
    Optional<Double> getDiscountPercentage(Integer userLevel);
}
