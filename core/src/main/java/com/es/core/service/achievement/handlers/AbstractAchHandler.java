package com.es.core.service.achievement.handlers;

import com.es.core.dao.AchievementDao;
import com.es.core.model.Context;
import com.es.core.model.user.Achievement;
import com.es.core.model.user.User;
import com.es.core.service.achievement.AchievementHandler;
import com.es.core.service.user.UserLevelService;

import javax.annotation.Resource;
import java.util.Optional;

public abstract class AbstractAchHandler implements AchievementHandler {
    protected String achievementId;

    @Resource
    private AchievementDao achievementDao;

    @Resource
    private UserLevelService userLevelService;

    @Override
    public void handle(Context context) {
        if (context.getUser() == null) throw new IllegalArgumentException();
        User user = context.getUser();
        Optional<Achievement> optAchievement = achievementDao.getById(achievementId);

        optAchievement.ifPresent(achievement -> {
            achievementDao.save(user.getName(), achievement.getId());
            userLevelService.addXP(user, achievement.getLevelPoints());
        });
    }

    public AchievementDao getAchievementDao() {
        return achievementDao;
    }

    public void setAchievementDao(AchievementDao achievementDao) {
        this.achievementDao = achievementDao;
    }

    public String getAchievementId() {
        return achievementId;
    }

    public void setAchievementId(String achievementId) {
        this.achievementId = achievementId;
    }
}
