package com.example.backend.controller;

import com.example.backend.dto.UsersJobsResponse;
import com.example.backend.dto.userDTO.StatsResponseDTO;
import com.example.backend.dto.userDTO.UserResponseDTO;
import com.example.backend.dto.userDTO.UserUpdateRequestDTO;
import com.example.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get current user details", description = "Retrieve details of the currently authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Current user details retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/users/current-user")
    public ResponseEntity<UserResponseDTO> getCurrentUser() {
        UserResponseDTO currentUser = userService.getCurrentUser();
        logger.info("Retrieved current user: {}", currentUser.getName());

        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }

    @Operation(summary = "Update user profile", description = "Update user profile details including avatar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User profile updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PutMapping("/users/update-user")
    public ResponseEntity<UserResponseDTO> updateUser(@RequestParam("avatar") MultipartFile avatar,
                                                      @ModelAttribute UserUpdateRequestDTO userUpdateRequest) {
        UserResponseDTO updatedUser = userService.updateUser(avatar, userUpdateRequest);
        logger.info("Updated user: {}", updatedUser.getName());

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @Operation(summary = "Get user statistics", description = "Get statistics for user activity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User statistics retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/users/stats")
    public ResponseEntity<StatsResponseDTO> showStats() {
        StatsResponseDTO stats = userService.showStats();
        logger.info("Retrieved user stats");

        return new ResponseEntity<>(stats, HttpStatus.OK);
    }

    @Operation(summary = "Get application statistics", description = "Retrieve statistics for the application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Application statistics retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/admin/app-stats")
    public ResponseEntity<UsersJobsResponse> getApplicationStats() {
        UsersJobsResponse applicationStats = userService.getApplicationStats();
        logger.info("Retrieved application stats");

        return new ResponseEntity<>(applicationStats, HttpStatus.OK);
    }

}
