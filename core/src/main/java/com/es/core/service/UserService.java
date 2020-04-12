package com.es.core.service;

import com.es.core.model.user.User;

public interface UserService {
    User getUserByName(String name);
    void saveUser(User user);
}
