package com.example.headhanter.service;

import com.example.headhanter.models.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public User getFakeAuthor() {
        return new User(1L, "Test", "Testov", "example@mail.ru");
    }
}