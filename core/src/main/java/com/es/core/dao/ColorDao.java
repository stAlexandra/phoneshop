package com.es.core.dao;

import com.es.core.model.phone.Color;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ColorDao {
    Set<Color> getColors(Long phoneId);
    Map<Long, Set<Color>> getColors(List<Long> phoneIds);
    void save(Set<Color> colors);
}
