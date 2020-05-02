package com.es.core.service.user;

import com.es.core.dao.UserDao;
import com.es.core.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User getUserByName(String name) {
        return userDao.get(name);
    }

    @Override
    public void saveUser(User user) {
        userDao.save(user);
    }
}
