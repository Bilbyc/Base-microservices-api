package com.base.users.controller;

import com.base.users.dto.AuthRequestDto;
import com.base.users.model.BaseUser;
import com.base.users.security.JwtUtil;
import com.base.users.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationConfiguration authenticationConfiguration;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationConfiguration authenticationConfiguration, UserService userService, JwtUtil jwtUtil) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody BaseUser baseUser) {
        return ResponseEntity.ok(userService.save(baseUser));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDto user) throws Exception {
        Authentication auth = authenticationConfiguration.getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
        );
        String token = jwtUtil.generateToken(user.getEmail());
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }
}
