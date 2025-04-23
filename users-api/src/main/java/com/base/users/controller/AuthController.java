package com.base.users.controller;

import com.base.users.dto.AuthRequestDto;
import com.base.users.dto.RegisterRequestDto;
import com.base.users.security.JwtUtil;
import com.base.users.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@Validated
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
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDto registerDto) {
            return ResponseEntity.ok(userService.save(registerDto));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDto authDto) {
        try {
            Authentication auth = authenticationConfiguration.getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(authDto.getEmail(), authDto.getPassword())
            );

            UserDetails userDetails = userService.loadUserByUsername(authDto.getEmail());
            String token = jwtUtil.generateToken(authDto.getEmail());

            return ResponseEntity.ok(Collections.singletonMap("token", token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciais inválidas.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno ao processar login.");
        }
    }
}
