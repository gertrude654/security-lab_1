package com.example.SecurityLab1.controller;


public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(int id) {
        super("User not found with ID: " + id);
    }
}
