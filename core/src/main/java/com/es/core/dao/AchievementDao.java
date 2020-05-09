package com.es.core.dao;

import com.es.core.model.user.Achievement;

import java.util.List;

public interface AchievementDao {
    Achievement getById(Long id);

    Achievement getByName(String name);

    List<Achievement> getByUserName(String userName);

    void save(Achievement achievement);
}
