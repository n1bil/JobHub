package com.example.backend.controller;

import com.example.backend.dto.AuthDTO.AuthRequestDTO;
import com.example.backend.dto.AuthDTO.AuthResponseDTO;
import com.example.backend.dto.userDTO.UserRequestDTO;
import com.example.backend.dto.userDTO.UserResponseDTO;
import com.example.backend.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthService userService;

    @Autowired
    public AuthController(AuthService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRequestDTO userRequest) {
        UserResponseDTO registeredUser = userService.register(userRequest);

        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthRequestDTO loginDto, HttpServletResponse response) {
        AuthResponseDTO authResponse = userService.login(loginDto);
        Cookie cookie = new Cookie("token", authResponse.getAccessToken());
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "User logged in");

        return ResponseEntity.ok(responseBody);
    }

}
