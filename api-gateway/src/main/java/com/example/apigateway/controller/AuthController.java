package com.example.apigateway.controller;

import com.example.apigateway.dto.AuthRequestDTO;
import com.example.apigateway.dto.AuthResponseDTO;
import com.example.apigateway.dto.RegisterRequestDTO;
import com.example.apigateway.dto.RegisterResponseDTO;
import com.example.apigateway.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.Cookie;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    @PostMapping("/auth/register")
    public ResponseEntity<RegisterResponseDTO> register(@Valid @RequestBody RegisterRequestDTO userRequest) {
        RegisterResponseDTO registeredUser = authService.registerUser(userRequest);
        logger.info("Registered user: {}", registeredUser.getName());

        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody AuthRequestDTO loginDto, HttpServletResponse response) {
        AuthResponseDTO authResponse = authService.loginUser(loginDto);
        String accessToken = authResponse.getAccessToken();

        Cookie cookie = new Cookie("token", accessToken);
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        logger.info("User logged in: {}", loginDto.getEmail());

        response.setHeader("Set-Cookie", String.format("token=%s; Path=/; Max-Age=86400; HttpOnly; Secure; SameSite=None", accessToken));

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "User logged in");

        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/users/current-user")
    public ResponseEntity<RegisterResponseDTO> getCurrentUser() {
        RegisterResponseDTO currentUser = authService.getCurrentUser();
        logger.info("Retrieved current user: {}", currentUser.getName());

        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }

}
