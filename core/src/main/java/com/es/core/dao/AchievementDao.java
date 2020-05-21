package com.es.core.dao;

import com.es.core.model.user.Achievement;

import java.util.List;
import java.util.Optional;

public interface AchievementDao {
    Optional<Achievement> getById(String id);

    Achievement getByName(String name);

    List<Achievement> getByUserName(String userName);

    void save(String userName, String achievementId);
}
