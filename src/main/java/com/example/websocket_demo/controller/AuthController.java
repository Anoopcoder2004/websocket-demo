package com.example.websocket_demo.controller;

import com.example.websocket_demo.model.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.websocket_demo.entity.User;
import com.example.websocket_demo.service.AuthService;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        return ResponseEntity.ok(authService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String result = authService.login(request.getUsername(), request.getPassword());
        if ("Login success".equals(result)) {
            return ResponseEntity.ok(Map.of("message", "Login success"));
        } else {
            return ResponseEntity.status(401).body(Map.of("message", result));
        }
    }

}
