package com.example.backend.repository;

import com.example.backend.entity.Job;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface JobRepository extends MongoRepository<Job, String> {

    List<Job> findAllByCreatedBy(String createdBy);

}
