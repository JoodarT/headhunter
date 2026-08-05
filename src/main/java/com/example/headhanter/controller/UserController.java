package com.example.headhanter.controller;

import com.example.headhanter.dto.UserDto;
import com.example.headhanter.models.User;
import com.example.headhanter.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;





    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDto userDto) {
        User created = userService.createUser(userDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @Valid @RequestBody UserDto userDto) {
        return userService.updateUser(id, userDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/search-by-name")
    public List<User> searchByName(@RequestParam String name) {
        return userService.getUsersByName(name);
    }

    @GetMapping("/search-by-phone")
    public List<User> searchByPhone(@RequestParam String phone) {
        return userService.getUsersByPhone(phone);
    }

    @GetMapping("/search-by-email")
    public User searchByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping("/exists")
    public boolean exists(@RequestParam String email) {
        return userService.checkUserExists(email);
    }

    @PostMapping("/upload-avatar/{userId}")
    public User uploadAvatar(
            @PathVariable Long userId,
            @RequestParam("file") MultipartFile file
    ) {

        return userService.uploadAvatar(userId, file);
    }
}