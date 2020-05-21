package com.es.core.service.achievement.handlers;

import com.es.core.model.Context;
import com.es.core.service.achievement.AchievementHandler;
import com.es.core.service.checkout.OrderService;

import javax.annotation.Resource;

public class FirstOrderAchHandler extends AbstractAchHandler implements AchievementHandler {
    @Resource
    private OrderService orderService;

    @Override
    public boolean meetsRequirements(final Context context) {
        Integer orderCount = orderService.getUserOrdersCount(context.getUser().getName());
        return orderCount != null && orderCount == 1;
    }
}
