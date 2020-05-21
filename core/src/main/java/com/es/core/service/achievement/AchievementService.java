package com.es.core.service.achievement;

import com.es.core.model.order.Order;

public interface AchievementService {
    void checkForOrderAchievements(Order order, String userName);
}
