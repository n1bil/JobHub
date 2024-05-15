package com.example.backend.service.impl;

import com.example.backend.dto.jobDTO.JobCreateRequestDTO;
import com.example.backend.dto.jobDTO.JobResponseDTO;
import com.example.backend.dto.jobDTO.JobUpdateRequestDTO;
import com.example.backend.dto.jobDTO.Jobs;
import com.example.backend.entity.Job;
import com.example.backend.entity.User;
import com.example.backend.exception.NotFoundException;
import com.example.backend.mapper.JobMapper;
import com.example.backend.repository.AuthRepository;
import com.example.backend.repository.JobRepository;
import com.example.backend.service.JobService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class JobServiceImpl implements JobService {

    private JobRepository jobRepository;
    private JobMapper jobMapper;
    private AuthRepository userRepository;
    private MongoTemplate mongoTemplate;

    @Autowired
    public JobServiceImpl(JobRepository jobRepository, JobMapper jobMapper, AuthRepository userRepository, MongoTemplate mongoTemplate) {
        this.jobRepository = jobRepository;
        this.jobMapper = jobMapper;
        this.userRepository = userRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Jobs createJob(JobCreateRequestDTO jobRequestDTO) {
        User user = getCurrentUser();

        Job job = jobMapper.mapToJob(jobRequestDTO);
        job.setCreatedBy(user);
        Job savedJob = jobRepository.save(job);

        return jobMapper.mapToJobResponseDTO(savedJob);
    }

    @Override
    public JobResponseDTO getAllJobsByUser(String search, String jobStatus, String jobType, String sort, int page, int limit) {
        User user = getCurrentUser();

        List<Jobs> jobs;
        long totalJobs;
        int numOfPages;

        if (user.getRole().equals("admin")) {
            Pageable pageable = PageRequest.of(page - 1, limit);
            Page<Job> jobsPage = jobRepository.findAll(pageable);
            jobs = jobsPage.getContent().stream()
                    .map(jobMapper::mapToJobResponseDTO)
                    .toList();
            totalJobs = jobsPage.getTotalElements();
            numOfPages = jobsPage.getTotalPages();
        } else {
            Criteria criteria = Criteria.where("createdBy.$id").is(new ObjectId(user.getId()));
            if (search != null && !search.isEmpty()) {
                criteria.orOperator(
                        Criteria.where("position").regex(search, "i"),
                        Criteria.where("company").regex(search, "i")
                );
            }

            if (jobStatus != null && !jobStatus.isEmpty() && !jobStatus.equals("all")) {
                criteria.and("jobStatus").is(jobStatus);
            }
            if (jobType != null && !jobType.isEmpty() && !jobType.equals("all")) {
                criteria.and("jobType").is(jobType);
            }

            Map<String, String> sortOptions = new HashMap<>();
            sortOptions.put("newest", "-createdAt");
            sortOptions.put("oldest", "createdAt");
            sortOptions.put("a-z", "position");
            sortOptions.put("z-a", "-position");

            String sortKey = sortOptions.getOrDefault(sort, sortOptions.get("newest"));

            Sort.Direction direction = sortKey.startsWith("-") ? Sort.Direction.DESC : Sort.Direction.ASC;
            String field = sortKey.startsWith("-") ? sortKey.substring(1) : sortKey;

            Pageable pageable = PageRequest.of(page - 1, limit);

            Aggregation aggregation = Aggregation.newAggregation(
                    match(criteria),
                    sort(direction, field),
                    skip(pageable.getOffset()),
                    limit(pageable.getPageSize())
            );

            totalJobs = mongoTemplate.count(Query.query(criteria), Job.class);
            numOfPages = (int) Math.ceil((double) totalJobs / limit);

            AggregationResults<Job> results = mongoTemplate.aggregate(aggregation, "jobs", Job.class);
            List<Job> filteredJobs = results.getMappedResults();

            jobs = filteredJobs.stream()
                    .map(jobMapper::mapToJobResponseDTO)
                    .collect(Collectors.toList());
        }

        return new JobResponseDTO(totalJobs, numOfPages, page, jobs);

//        if (user.getRole().equals("admin")) {
//            return jobRepository.findAll().stream()
//                    .map(jobMapper::mapToJobResponseDTO)
//                    .collect(Collectors.toList());
//        } else {
//            List<Job> jobs = jobRepository.findAllByCreatedBy(user.getId());
//
//            return jobs.stream()
//                    .map(jobMapper::mapToJobResponseDTO)
//                    .collect(Collectors.toList());
//        }
    }

    @Override
    public Jobs getJob(String jobId) {
        User user = getCurrentUser();
        Job foundJob = jobRepository.findById(jobId).orElseThrow(() -> new NotFoundException("Invalid id"));

        if (user.getId().equals(foundJob.getCreatedBy().getId())) {
            return jobMapper.mapToJobResponseDTO(foundJob);
        } else {
            throw new AccessDeniedException("You do not have permission to access this job");
        }
    }

    @Override
    public Jobs updateJobById(JobUpdateRequestDTO requestJob, String jobId) {
        getCurrentUser();
        Job job = jobRepository.findById(jobId).orElseThrow(() -> new NotFoundException("Invalid id"));

        job.setCompany(requestJob.getCompany());
        job.setPosition(requestJob.getPosition());
        job.setJobStatus(requestJob.getJobStatus());
        job.setJobType(requestJob.getJobType());
        job.setPosition(requestJob.getPosition());

        Job updatedJob = jobRepository.save(job);

        return jobMapper.mapToJobResponseDTO(updatedJob);
    }

    @Override
    public void deleteJobById(String jobId) {
        getCurrentUser();
        Job job = jobRepository.findById(jobId).orElseThrow(() -> new NotFoundException("Invalid id"));

        jobRepository.delete(job);
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email).orElseThrow(() -> new AccessDeniedException("Access denied"));
    }
}
