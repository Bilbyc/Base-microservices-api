package com.base.users.controller;

import com.base.users.model.BaseUser;
import com.base.users.security.JwtUtil;
import com.base.users.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthenticationConfiguration authenticationConfiguration;
    private UserService userService;
    private JwtUtil jwtUtil;

    public AuthController(AuthenticationConfiguration authenticationConfiguration, UserService userService, JwtUtil jwtUtil) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> login(@RequestBody BaseUser baseUser) {
        return ResponseEntity.ok(userService.save(baseUser));
    }
}
