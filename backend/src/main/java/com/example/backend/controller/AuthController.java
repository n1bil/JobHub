package com.example.backend.controller;

import com.example.backend.dto.AuthDTO.AuthRequestDTO;
import com.example.backend.dto.AuthDTO.AuthResponseDTO;
import com.example.backend.dto.userDTO.UserRequestDTO;
import com.example.backend.dto.userDTO.UserResponseDTO;
import com.example.backend.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private AuthService userService;

    @Autowired
    public AuthController(AuthService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Register a new user", description = "Register a new user with the provided details")
    @ApiResponse(responseCode = "201", description = "User successfully registered")
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRequestDTO userRequest) {
        UserResponseDTO registeredUser = userService.register(userRequest);
        logger.info("Registered user: {}", registeredUser.getName());

        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @Operation(summary = "Login with credentials", description = "Authenticate user with provided credentials and generate access token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged in successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody AuthRequestDTO loginDto, HttpServletResponse response) {
        AuthResponseDTO authResponse = userService.login(loginDto);
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

}
