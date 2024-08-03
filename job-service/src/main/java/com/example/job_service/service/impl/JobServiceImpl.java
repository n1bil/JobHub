package com.example.job_service.service.impl;

import com.example.job_service.dto.UsersCountResponse;
import com.example.job_service.dto.UsersJobsCountResponse;
import com.example.job_service.dto.jobDTO.JobCreateRequestDTO;
import com.example.job_service.dto.jobDTO.JobUpdateRequestDTO;
import com.example.job_service.dto.jobDTO.JobsResponseDTO;
import com.example.job_service.dto.jobDTO.JobResponseDTO;
import com.example.job_service.entity.Job;
import com.example.job_service.exception.NotFoundException;
import com.example.job_service.mapper.JobMapper;
import com.example.job_service.repository.JobRepository;
import com.example.job_service.service.JobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final JobMapper jobMapper;
    private final MongoTemplate mongoTemplate;
    private final WebClient.Builder webClientBuilder;

    @Override
    public JobResponseDTO createJob(JobCreateRequestDTO jobRequestDTO) {

        Job job = jobMapper.mapToJob(jobRequestDTO);
        Job savedJob = jobRepository.save(job);
        log.info("Job {} is saved", job.getId());

        return jobMapper.mapToJobResponseDTO(savedJob);
    }

    @Override
//    @Cacheable(value = "userJobs", key = "{#search, #jobStatus, #jobType, #sort, #page, #limit}")
    public JobsResponseDTO getAllJobsByUser(String search, String jobStatus, String jobType, String sort, int page, int limit) {
        List<JobResponseDTO> jobs;
        long totalJobs;
        int numOfPages;

        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<Job> jobsPage = jobRepository.findAll(pageable);
        jobs = jobsPage.getContent().stream()
                .map(jobMapper::mapToJobResponseDTO)
                .toList();
        totalJobs = jobsPage.getTotalElements();
        numOfPages = jobsPage.getTotalPages();

        /*
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

         */

        return new JobsResponseDTO(totalJobs, numOfPages, page, jobs);

    }

    @Override
    public UsersJobsCountResponse getApplicationStats() {
        UsersCountResponse usersCountResponse = webClientBuilder.build().get()
                .uri("http://user-service/api/v1/admin/app-userstats")
                .retrieve()
                .bodyToMono(UsersCountResponse.class)
                .block();

        long jobCount = mongoTemplate.count(new Query(), Job.class);

        UsersJobsCountResponse usersJobsResponse = new UsersJobsCountResponse();
        usersJobsResponse.setUsers(usersCountResponse != null ? usersCountResponse.getUsers() : 0);
        usersJobsResponse.setJobs(jobCount);

        return usersJobsResponse;
    }


    @Override
//    @Cacheable(value = "jobs", key = "#jobId")
    public JobResponseDTO getJob(String jobId) {
//        User user = getCurrentUser();
        Job foundJob = jobRepository.findById(jobId).orElseThrow(() -> new NotFoundException("Invalid id"));

//        if (user.getId().equals(foundJob.getCreatedBy().getId())) {
            return jobMapper.mapToJobResponseDTO(foundJob);
//        } else {
//            throw new AccessDeniedException("You do not have permission to access this job");
//        }
    }


    @Override
    public JobResponseDTO updateJobById(JobUpdateRequestDTO requestJob, String jobId) {
//        getCurrentUser();
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
//        getCurrentUser();
        Job job = jobRepository.findById(jobId).orElseThrow(() -> new NotFoundException("Invalid id"));

        jobRepository.delete(job);
    }

//    public User getCurrentUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String email = authentication.getName();
//        return userRepository.findByEmail(email).orElseThrow(() -> new AccessDeniedException("Access denied"));
//    }

}
