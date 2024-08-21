//package com.example.SecurityLab1.service;
//
//import com.example.SecurityLab1.model.User;
//
//import com.example.SecurityLab1.repo.UserRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class UserService {
//
//
//    private UserRepo userRepo;
//
//    @Autowired
//    public UserService(UserRepo userRepo) {
//        this.userRepo = userRepo;
//    }
//
//    public User saveUser(User user) {
//        // Validate and save user
//        return userRepo.save(user);
//    }
//    public List<User> AllUsers() {
//        return userRepo.findAll();
//    }
//
//    public User getUserById(int id) {
//        return userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
//    }
//
//}


package com.example.SecurityLab1.service;

import com.example.SecurityLab1.model.User;
import com.example.SecurityLab1.repo.UserRepo;
import org.apache.commons.lang3.StringEscapeUtils;
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
        return user.map(this::sanitizeUser).orElse(null);
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
       // return StringEscapeUtils.escapeHtml4(input);
        return HtmlUtils.htmlEscape(input);

    }
}
