package com.example.backend.controller;

import com.example.backend.dto.AuthDTO.AuthRequestDTO;
import com.example.backend.dto.AuthDTO.AuthResponseDTO;
import com.example.backend.dto.userDTO.UserRequestDTO;
import com.example.backend.dto.userDTO.UserResponseDTO;
import com.example.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AuthController {

    private UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRequestDTO userRequest) {
        UserResponseDTO registeredUser = userService.register(userRequest);

        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO loginDto) {
        AuthResponseDTO authResponse = userService.login(loginDto);


        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

}
