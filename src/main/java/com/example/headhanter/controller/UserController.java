package com.example.headhanter.controller;

import com.example.headhanter.models.User;
import com.example.headhanter.models.AccountType;
import com.example.headhanter.service.UserService;
import com.example.headhanter.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User created = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/applicants")
    public ResponseEntity<List<User>> getApplicants() {
        return ResponseEntity.ok(userService.getUsersByType(AccountType.APPLICANT));
    }

    @GetMapping("/employers")
    public ResponseEntity<List<User>> getEmployers() {
        return ResponseEntity.ok(userService.getUsersByType(AccountType.EMPLOYER));
    }

    @PostMapping(value = "/{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        if (userService.getUserById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        String fileName = fileService.saveUploadedFile(file, "/avatars");
        userService.updateAvatar(id, fileName);
        return ResponseEntity.ok(fileName);
    }

    @GetMapping("/{id}/avatar")
    public ResponseEntity<?> getAvatar(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(User::getAvatarFileName)
                .map(name -> fileService.getOutputFile(name, "/avatars", MediaType.IMAGE_JPEG))
                .orElse(ResponseEntity.notFound().build());
    }
}