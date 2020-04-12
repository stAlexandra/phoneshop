package com.es.core.dao;

import com.es.core.model.user.User;

public interface UserDao {
    User get(String username);
    void save(User user);
}
