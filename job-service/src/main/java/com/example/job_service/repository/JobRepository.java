package com.example.job_service.repository;

import com.example.job_service.entity.Job;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JobRepository extends MongoRepository<Job, String> {

//    List<Job> findAllByCreatedBy(String createdBy);
//
//    List<Job> findAllByCreatedByAndCompanyContainingOrPositionContaining(String createdBy, String company, String position);
//
//    List<Job> findAllByCreatedByAndCompanyContainingOrPositionContainingOrJobStatusOrJobType(String createdBy, String company, String position, String jobStatus, String jobType);
}
