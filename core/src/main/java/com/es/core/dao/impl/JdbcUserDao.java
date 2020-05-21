package com.es.core.dao.impl;

import com.es.core.dao.AchievementDao;
import com.es.core.dao.UserDao;
import com.es.core.dao.mappers.UserRowMapper;
import com.es.core.model.user.Level;
import com.es.core.model.user.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Transactional
@Repository
public class JdbcUserDao implements UserDao {
    private static final String SQL_UPDATE_USER = "UPDATE users SET level=:level, xp=:xp WHERE username=:name";
    private static final String SQL_GET_USER_BY_NAME = "SELECT * FROM users LEFT JOIN levels ON users.level = levels.number WHERE username = :name";

    @Resource
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Resource
    private UserRowMapper userRowMapper;
    @Resource
    private AchievementDao achievementDao;
//    @Resource
//    private OrderDao orderDao;

    @Override
    @Transactional(readOnly = true)
    public User get(String name) {
        RowMapper<Level> levelRowMapper = new BeanPropertyRowMapper<>(Level.class);

        MapSqlParameterSource params = new MapSqlParameterSource("name", name);
        return jdbcTemplate.query(SQL_GET_USER_BY_NAME, params, resultSet -> {
            if (resultSet.next()) {
                User user = userRowMapper.mapRow(resultSet, 1);
                user.setLevel(levelRowMapper.mapRow(resultSet, 1));
                user.setAchievements(achievementDao.getByUserName(user.getName()));
                //user.setOrders(orderDao.getByUserName(user.getName()));
                return user;
            }
            return null;
        });
    }

    @Override
    public void save(User user) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", user.getName());
        params.addValue("level", user.getLevel().getNumber());
        params.addValue("xp", user.getXp());
        jdbcTemplate.update(SQL_UPDATE_USER, params);
    }
}
