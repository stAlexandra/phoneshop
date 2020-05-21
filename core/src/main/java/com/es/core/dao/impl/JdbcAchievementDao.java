package com.es.core.dao.impl;

import com.es.core.dao.AchievementDao;
import com.es.core.model.user.Achievement;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcAchievementDao implements AchievementDao {
    private static final String SQL_GET_BY_USER_NAME = "SELECT * FROM achievements AS a " +
            "INNER JOIN users2achievements AS u2a ON a.id = u2a.achievementId " +
            "WHERE u2a.username=:userName";
    private static final String SQL_GET_BY_ID = "SELECT * FROM achievements a " +
            "WHERE a.id=:id";
    private static final String SQL_SAVE_FOR_USER = "INSERT INTO users2achievements (username, achievementId) " +
            "VALUES (:userName, :achievementId)";
    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Optional<Achievement> getById(String id) {
        SqlParameterSource params = new MapSqlParameterSource("id", id);
        List<Achievement> achievementList = new ArrayList<>();
        achievementList = jdbcTemplate.query(SQL_GET_BY_ID, params,
                new BeanPropertyRowMapper<>(Achievement.class));
        return Optional.ofNullable(achievementList.get(0));
    }

    @Override
    public Achievement getByName(String name) {
        return null;
    }

    @Override
    public List<Achievement> getByUserName(String userName) {
        SqlParameterSource params = new MapSqlParameterSource("userName", userName);
        return jdbcTemplate.query(SQL_GET_BY_USER_NAME, params,
                new BeanPropertyRowMapper<>(Achievement.class));
    }

    @Override
    public void save(String userName, String achievementId) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userName", userName);
        params.addValue("achievementId", achievementId);
        jdbcTemplate.update(SQL_SAVE_FOR_USER, params);
    }
}
