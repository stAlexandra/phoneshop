package com.es.core.dao.impl;

import com.es.core.dao.AchievementDao;
import com.es.core.model.user.Achievement;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class JdbcAchievementDao implements AchievementDao {
    private static final String SQL_GET_BY_USER_NAME = "SELECT * FROM achievements AS a " +
            "INNER JOIN users2achievements AS u2a ON a.id = u2a.achievementId " +
            "WHERE u2a.username=:userName";

    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Achievement getById(Long id) {
        return null;
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
    public void save(Achievement achievement) {

    }
}
