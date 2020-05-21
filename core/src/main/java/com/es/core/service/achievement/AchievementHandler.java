package com.es.core.service.achievement;

import com.es.core.model.Context;

public interface AchievementHandler {
    void handle(Context context);
    boolean meetsRequirements(Context context);
}
