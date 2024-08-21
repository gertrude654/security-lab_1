package com.example.SecurityLab1.service;

import com.example.SecurityLab1.controller.UserNotFoundException;
import com.example.SecurityLab1.model.User;
import com.example.SecurityLab1.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepository;

    public User saveUser(User user) {
        // Sanitize inputs before saving to the database
        user.setFirstName(escapeHtml(user.getFirstName()));
        user.setLastName(escapeHtml(user.getLastName()));
        user.setEmail(escapeHtml(user.getEmail()));

        return userRepository.save(user);
    }

    public User getUserById(int id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(this::sanitizeUser).orElseThrow(() -> new UserNotFoundException(id));
    }

    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        // Sanitize each user in the list before returning
        users.forEach(this::sanitizeUser);
        return users;
    }

    private User sanitizeUser(User user) {
        // Sanitize or encode user data before returning
        user.setFirstName(escapeHtml(user.getFirstName()));
        user.setLastName(escapeHtml(user.getLastName()));
        user.setEmail(escapeHtml(user.getEmail()));
        return user;
    }

    private String escapeHtml(String input) {
        return HtmlUtils.htmlEscape(input); // Sanitize input to prevent XSS
    }
}
