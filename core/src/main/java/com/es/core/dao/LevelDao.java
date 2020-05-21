package com.es.core.dao;

import com.es.core.model.user.Level;

public interface LevelDao {
    Level getByNumber(int levelNumber);
}
