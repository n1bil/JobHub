package com.example.backend.service.impl;

import com.example.backend.controller.JobController;
import com.example.backend.dto.UsersJobsResponse;
import com.example.backend.dto.userDTO.StatsResponseDTO;
import com.example.backend.dto.userDTO.UserResponseDTO;
import com.example.backend.dto.userDTO.UserUpdateRequestDTO;
import com.example.backend.entity.Job;
import com.example.backend.entity.User;
import com.example.backend.mapper.UserMapper;
import com.example.backend.repository.AuthRepository;
import com.example.backend.service.UserService;
import com.example.backend.utils.CloudinaryService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(JobController.class);
    private AuthRepository authRepository;
    private UserMapper userMapper;
    private MongoTemplate mongoTemplate;
    private CloudinaryService cloudinaryService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(AuthRepository authRepository, UserMapper userMapper, MongoTemplate mongoTemplate, CloudinaryService cloudinaryService, PasswordEncoder passwordEncoder) {
        this.authRepository = authRepository;
        this.userMapper = userMapper;
        this.mongoTemplate = mongoTemplate;
        this.cloudinaryService = cloudinaryService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
//    @Cacheable(value = "users", key = "#root.target.getCurrentAuthUser().id")
    public UserResponseDTO getCurrentUser() {
        User user = getCurrentAuthUser();

        return userMapper.mapToResponseDTO(user);
    }

    @Override
    public UserResponseDTO updateUser(MultipartFile avatar, UserUpdateRequestDTO userUpdateRequest) {
        User user = getCurrentAuthUser();

        user.setName(userUpdateRequest.getName());
        user.setLastName(userUpdateRequest.getLastName());
        if (!userUpdateRequest.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userUpdateRequest.getPassword()));
        }
        user.setLocation(userUpdateRequest.getLocation());

        if (avatar != null && !avatar.isEmpty()) {
            try {
                Map<?, ?> result = cloudinaryService.upload(avatar);
                String avatarUrl = (String) result.get("secure_url");
                String publicId = (String) result.get("public_id");
                user.setAvatar(avatarUrl);
                user.setAvatarPublicId(publicId);
            } catch (IOException e) {
                String errorMessage = "An error occurred while uploading avatar for user with email: " + user.getEmail();
                logger.error(errorMessage, e);
                throw new RuntimeException(errorMessage);
            }
        }

        authRepository.save(user);

        return userMapper.mapToResponseDTO(user);
    }

    @Override
//    @Cacheable(value = "applicationStatsCache", key = "'applicationStats'", unless = "#result == null")
    public UsersJobsResponse getApplicationStats() {
        long userCount = mongoTemplate.count(new Query(), User.class);
        long jobCount = mongoTemplate.count(new Query(), Job.class);

        UsersJobsResponse usersJobsResponse = new UsersJobsResponse();
        usersJobsResponse.setUsers(userCount);
        usersJobsResponse.setJobs(jobCount);

        return usersJobsResponse;
    }

    @Override
//    @Cacheable(value = "stats", key = "#root.target.getCurrentAuthUser().id")
    public StatsResponseDTO showStats() {
        User user = getCurrentAuthUser();

        Aggregation aggregation = newAggregation(
                match(Criteria.where("createdBy.$id").is(new ObjectId(user.getId()))),
                group("jobStatus").count().as("count")
        );

        AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, "jobs", Map.class);
        List<Map> mappedResults = results.getMappedResults();

        Map<String, Integer> formattedResults = new HashMap<>();
        for (Map map : mappedResults) {
            String status = (String) map.get("_id");
            Integer count = ((Number) map.get("count")).intValue();
            formattedResults.put(status, count);
        }

        String[] possibleStatuses = {"pending", "interview", "declined"};
        for (String status : possibleStatuses) {
            formattedResults.putIfAbsent(status, 0);
        }

        Aggregation aggregation2 = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("createdBy.$id").is(new ObjectId(user.getId()))),
                Aggregation.project()
                        .andExpression("dateToString('%b %Y', createdAt)").as("date"),
                Aggregation.group("date")
                        .count().as("count"),
                Aggregation.project()
                        .and("_id").as("date")
                        .and("count").as("count"),
                Aggregation.sort(Sort.Direction.DESC, "date"),
                Aggregation.limit(6),
                Aggregation.project().andExclude("_id")
        );

        AggregationResults<Map> results2 = mongoTemplate.aggregate(aggregation2, "jobs", Map.class);
        List<Map> mappedResults2 = results2.getMappedResults();

        StatsResponseDTO responseDTO = new StatsResponseDTO();
        responseDTO.setDefaultStats(formattedResults);
        responseDTO.setMonthlyApplications(mappedResults2);

        return responseDTO;
    }


    public User getCurrentAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return authRepository.findByEmail(email)
                .orElseThrow(() -> new AccessDeniedException("Access denied"));
    }

}