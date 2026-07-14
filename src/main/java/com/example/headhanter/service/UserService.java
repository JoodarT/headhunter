package com.example.headhanter.service;

import com.example.headhanter.models.User;
import com.example.headhanter.models.AccountType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final List<User> users = new ArrayList<>();
    private long currentId = 1;

    public User save(User user) {
        user.setId(currentId++);
        users.add(user);
        return user;
    }

    public Optional<User> getUserById(Long id) {
        return users.stream().filter(u -> u.getId().equals(id)).findFirst();
    }

    public List<User> getUsersByType(AccountType type) {
        return users.stream().filter(u -> u.getAccountType() == type).toList();
    }

    public void updateAvatar(Long userId, String fileName) {
        getUserById(userId).ifPresent(u -> u.setAvatarFileName(fileName));
    }
}