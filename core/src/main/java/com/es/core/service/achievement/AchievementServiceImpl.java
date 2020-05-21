package com.es.core.service.achievement;

import com.es.core.model.Context;
import com.es.core.model.order.Order;
import com.es.core.model.user.User;
import com.es.core.service.user.UserService;

import javax.annotation.Resource;
import java.util.List;

public class AchievementServiceImpl implements AchievementService {
    private List<AchievementHandler> handlers;

    @Resource
    private UserService userService;

    @Override
    public void checkForOrderAchievements(Order order, String userName) {
        User user = userService.getUserByName(userName);
        Context context = new Context(user, order);
        handlers.stream().filter(handler -> handler.meetsRequirements(context)).forEach(handler -> handler.handle(context));
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setHandlers(List<AchievementHandler> handlers) {
        this.handlers = handlers;
    }
}
