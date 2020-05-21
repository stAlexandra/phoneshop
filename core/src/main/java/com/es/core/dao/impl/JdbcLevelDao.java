package com.es.core.dao.impl;

import com.es.core.dao.LevelDao;
import com.es.core.model.user.Level;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Repository
public class JdbcLevelDao implements LevelDao {
    private static final String SQL_GET_BY_NUMBER = "SELECT * FROM levels WHERE levels.number=:levelNumber";

    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Level getByNumber(int levelNumber) {
        List<Level> levelList = jdbcTemplate.query(SQL_GET_BY_NUMBER, Collections.singletonMap("levelNumber", levelNumber),
                new BeanPropertyRowMapper<>(Level.class));
        return CollectionUtils.isEmpty(levelList) ? null : levelList.get(0);
    }
}
