package com.example.backend.repository;

import com.example.backend.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AuthRepository extends MongoRepository<User, String> {

    Boolean existsUserByEmail(String email);

    Optional<User> findByEmail(String email);

}
