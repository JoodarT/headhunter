package com.example.headhanter.service;

import com.example.headhanter.dao.UserDao;
import com.example.headhanter.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;

    public List<User> getUsersByName(String name) {
        return userDao.findByName(name);
    }

    public List<User> getUsersByPhone(String phone) {
        return userDao.findByPhone(phone);
    }

    public User getUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    public boolean checkUserExists(String email) {
        return userDao.existsByEmail(email);
    }

    public User createUser(User user) {
        return userDao.save(user);
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public User getUserById(Long id) {
        return userDao.findById(id);
    }

    public User updateUser(Long id, User updatedUser) {
        User existingUser = userDao.findById(id);
        if (existingUser != null) {
            existingUser.setName(updatedUser.getName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setPhone(updatedUser.getPhone());
            existingUser.setAccountType(updatedUser.getAccountType());
            userDao.update(existingUser);
            return existingUser;
        }
        return null;
    }

    public boolean deleteUser(Long id) {
        if (userDao.findById(id) != null) {
            userDao.deleteById(id);
            return true;
        }
        return false;
    }
}