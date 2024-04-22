package com.example.backend.repository;

import com.example.backend.entity.Job;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface JobRepository extends MongoRepository<Job, String> {

    List<Job> findAllByCreatedBy(String createdBy);

    List<Job> findAllByCreatedByAndCompanyContainingOrPositionContaining(String createdBy, String company, String position);

    List<Job> findAllByCreatedByAndCompanyContainingOrPositionContainingOrJobStatusOrJobType(String createdBy, String company, String position, String jobStatus, String jobType);
}
