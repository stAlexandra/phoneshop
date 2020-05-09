package com.es.core.dao.impl;

import com.es.core.dao.AchievementDao;
import com.es.core.dao.UserDao;
import com.es.core.dao.mappers.UserRowMapper;
import com.es.core.model.user.User;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Transactional
@Repository
public class JdbcUserDao implements UserDao {
    private static final String SQL_INSERT_USER = "INSERT INTO users (username, enabled, level) VALUES ( :name, :enabled, :level )";
    private static final String SQL_GET_USER_BY_NAME = "SELECT * FROM users WHERE username = :name";

    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Resource
    private UserRowMapper userRowMapper;

    @Resource
    private AchievementDao achievementDao;

    @Override
    @Transactional(readOnly = true)
    public User get(String name) {
        MapSqlParameterSource params = new MapSqlParameterSource("name", name);
        return jdbcTemplate.query(SQL_GET_USER_BY_NAME, params, resultSet -> {
            if (resultSet.next()) {
                User user = userRowMapper.mapRow(resultSet, 1);
                user.setAchievements(achievementDao.getByUserName(user.getName()));
                return user;
            }
            return null;
        });
    }

    @Override
    public void save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        jdbcTemplate.update(SQL_INSERT_USER, parameterSource);
    }
}
