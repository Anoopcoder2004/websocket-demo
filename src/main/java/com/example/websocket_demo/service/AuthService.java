package com.example.websocket_demo.service;

import com.example.websocket_demo.entity.User;
import com.example.websocket_demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public String register(User user) {

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return "User already exists";
        }

        return userRepository.save(user) != null
                ? "Registration successful"
                : "Error registering user";
    }

    public String login(String username, String password) {

        User user = userRepository.findByUsername(username)
                .orElse(null);

        if (user == null)
            return "User not found";

        if (!user.getPassword().equals(password))
            return "Invalid password";

        return "Login success";
    }
}
