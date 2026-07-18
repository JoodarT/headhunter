package com.example.headhanter.service;

import com.example.headhanter.dao.UserDao;
import com.example.headhanter.models.User;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> searchUsersByName(String name) {
        return userDao.findByName(name);
    }

    public boolean checkUserExists(String email) {
        return userDao.existsByEmail(email);
    }
}