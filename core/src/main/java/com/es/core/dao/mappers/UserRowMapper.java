package com.es.core.dao.mappers;

import com.es.core.model.user.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.es.core.dao.DbContract.UsersTable.*;

@Component
public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setName(resultSet.getString(USERNAME));
        user.setEnabled(resultSet.getBoolean(ENABLED));
        user.setExperiencePoints(resultSet.getLong(XP));
        return user;
    }
}
