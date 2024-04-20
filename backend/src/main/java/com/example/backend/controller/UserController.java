package com.example.backend.controller;

import com.example.backend.dto.UsersJobsResponse;
import com.example.backend.dto.userDTO.UserResponseDTO;
import com.example.backend.dto.userDTO.UserUpdateRequestDTO;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/current-user")
    public ResponseEntity<UserResponseDTO> getCurrentUser() {
        UserResponseDTO currentUser = userService.getCurrentUser();

        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }

    @PutMapping("/users/update-user")
    public ResponseEntity<UserResponseDTO> updateUser(@RequestParam("avatar") MultipartFile avatar,
                                                      @ModelAttribute UserUpdateRequestDTO userUpdateRequest) {
        UserResponseDTO updatedUser = userService.updateUser(avatar, userUpdateRequest);

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @GetMapping("/admin/app-stats")
    public ResponseEntity<UsersJobsResponse> getApplicationStats() {
        UsersJobsResponse applicationStats = userService.getApplicationStats();

        return new ResponseEntity<>(applicationStats, HttpStatus.OK);
    }

    @GetMapping("/admin/test")
    public ResponseEntity<Map<String, String>> test() {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "test route");

        return ResponseEntity.ok(responseBody);
    }
}