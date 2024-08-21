package com.example.SecurityLab1.controller;

import com.example.SecurityLab1.model.User;
import com.example.SecurityLab1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User createdUser = userService.saveUser(user);
        return ResponseEntity.ok(encodeUser(createdUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(encodeUser(user));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(encodeUsers(users));
    }

    // Method to encode user data for output
    private User encodeUser(User user) {
        if (user != null) {
            user.setFirstName(HtmlUtils.htmlEscape(user.getFirstName()));
            user.setLastName(HtmlUtils.htmlEscape(user.getLastName()));
            user.setEmail(HtmlUtils.htmlEscape(user.getEmail()));
        }
        return user;
    }

    // Method to encode a list of users
    private List<User> encodeUsers(List<User> users) {
        for (User user : users) {
            encodeUser(user);
        }
        return users;
    }
}
