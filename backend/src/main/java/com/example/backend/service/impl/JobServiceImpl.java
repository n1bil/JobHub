package com.example.backend.service.impl;

import com.example.backend.dto.jobDTO.JobCreateRequestDTO;
import com.example.backend.dto.jobDTO.JobResponseDTO;
import com.example.backend.dto.jobDTO.JobUpdateRequestDTO;
import com.example.backend.entity.Job;
import com.example.backend.entity.User;
import com.example.backend.exception.NotFoundException;
import com.example.backend.mapper.JobMapper;
import com.example.backend.repository.JobRepository;
import com.example.backend.repository.AuthRepository;
import com.example.backend.service.JobService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
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
    public JobResponseDTO createJob(JobCreateRequestDTO jobRequestDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User foundUser = userRepository.findByEmail(email).orElseThrow(() ->
                new NotFoundException("User with email " + email + " not found"));

        Job job = jobMapper.mapToJob(jobRequestDTO);
        job.setCreatedBy(foundUser);
        Job savedJob = jobRepository.save(job);

        return jobMapper.mapToJobResponseDTO(savedJob);
    }

    @Override
    public List<JobResponseDTO> getAllJobsByUser(String search, String jobStatus, String jobType, String sort) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User with email " + email + " not found"));

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
        System.out.println(sortKey);

        Sort.Direction direction = sortKey.startsWith("-") ? Sort.Direction.DESC : Sort.Direction.ASC;
        String field = sortKey.startsWith("-") ? sortKey.substring(1) : sortKey;

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.sort(direction, field)
        );

        AggregationResults<Job> results = mongoTemplate.aggregate(aggregation, "jobs", Job.class);
        List<Job> filteredJobs = results.getMappedResults();

        return filteredJobs.stream()
                .map(jobMapper::mapToJobResponseDTO)
                .collect(Collectors.toList());

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
    public JobResponseDTO getJob(String jobId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User with email " + email + " not found"));
        Job foundJob = jobRepository.findById(jobId).orElseThrow(() -> new NotFoundException("Invalid id"));


        if (user.getId().equals(foundJob.getCreatedBy().getId())) {
            return jobMapper.mapToJobResponseDTO(foundJob);
        } else {
            throw new AccessDeniedException("You do not have permission to access this job");
        }
    }

    @Override
    public JobResponseDTO updateJobById(JobUpdateRequestDTO requestJob, String jobId) {
        Job job = jobRepository.findById(jobId).orElseThrow(() -> new NotFoundException("Job with id '" + jobId + "' not found"));

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
        Job job = jobRepository.findById(jobId).orElseThrow(() -> new NotFoundException("Job with id '" + jobId + "' not found"));

        jobRepository.delete(job);
    }
}
