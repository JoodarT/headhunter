package com.example.headhanter.service;

import com.example.headhanter.models.User;
import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(Long id);
    User findByEmail(String email);
    User create(User user);
    User update(Long id, User userDetails);
}