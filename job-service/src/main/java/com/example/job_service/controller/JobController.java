package com.example.job_service.controller;

import com.example.job_service.dto.UsersJobsCountResponse;
import com.example.job_service.dto.jobDTO.JobCreateRequestDTO;
import com.example.job_service.dto.jobDTO.JobsResponseDTO;
import com.example.job_service.dto.jobDTO.JobResponseDTO;
import com.example.job_service.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/jobs")
@ResponseStatus(HttpStatus.CREATED)
@RequiredArgsConstructor
public class JobController {

    private static final Logger logger = LoggerFactory.getLogger(JobController.class);

    private final JobService jobService;

    @Operation(summary = "Get all jobs", description = "Endpoint to retrieve all jobs")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved jobs")
    @SecurityRequirement(name = "Bear Authentication")
    @GetMapping()
    public ResponseEntity<JobsResponseDTO> getJobs(@RequestParam(value = "search", required = false) String search,
                                                   @RequestParam(value = "jobStatus", required = false) String jobStatus,
                                                   @RequestParam(value = "jobType", required = false) String jobType,
                                                   @RequestParam(defaultValue = "newest") String sort,
                                                   @RequestParam(defaultValue = "1") int page,
                                                   @RequestParam(defaultValue = "10") int limit) {
        JobsResponseDTO allJobs = jobService.getAllJobsByUser(search, jobStatus, jobType, sort, page, limit);
        logger.info("Retrieved all jobs. Total count: {}", allJobs.getJobs().size());

        return ResponseEntity.ok(allJobs);
    }

    @Operation(summary = "Create a new job", description = "Create a new job with the provided details")
    @ApiResponse(responseCode = "201", description = "Job created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @SecurityRequirement(name = "Bear Authentication")
    @PostMapping()
    public ResponseEntity<JobResponseDTO> addJob(@Valid @RequestBody JobCreateRequestDTO jobRequest) {
        JobResponseDTO createdJob = jobService.createJob(jobRequest);
        logger.info("Created job with ID: {}", createdJob.getId());

        return new ResponseEntity<>(createdJob, HttpStatus.CREATED);
    }

    @GetMapping("/admin/app-stats")
    public ResponseEntity<UsersJobsCountResponse> getUsersJobsCount() {
        Mono<UsersJobsCountResponse> applicationStats = jobService.getApplicationStats();

        return ResponseEntity.ok(applicationStats.block());
    }

    /*
    @Operation(summary = "Get job by ID", description = "Get job details by its ID from the database")
    @ApiResponse(responseCode = "200", description = "Job details retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Job with the provided ID not found")
    @SecurityRequirement(name = "Bear Authentication")
    @GetMapping("/{job_id}")
    public ResponseEntity<Jobs> getJobById(@PathVariable String job_id) {
        Jobs receivedJob = jobService.getJob(job_id);

        if (receivedJob != null) {
            logger.info("Retrieved job with ID: {}", job_id);
            return ResponseEntity.ok(receivedJob);
        } else {
            logger.warn("Job with ID {} not found", job_id);
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Update a job by ID", description = "Update an existing job by its ID")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 OK")
    @ApiResponse(responseCode = "404", description = "Job not found")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @SecurityRequirement(name = "Bear Authentication")
    @PutMapping("/{job_id}")
    public ResponseEntity<Jobs> editJobById(@Valid @RequestBody JobUpdateRequestDTO requestJob,
                                            @PathVariable String job_id) {
        Jobs jobResponseDTO = jobService.updateJobById(requestJob, job_id);
        logger.info("Updated job with ID: {}", job_id);

        return new ResponseEntity<>(jobResponseDTO, HttpStatus.OK);
    }

    @Operation(summary = "Delete a job by ID", description = "Delete an existing job by its ID")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 OK")
    @ApiResponse(responseCode = "404", description = "Job not found")
    @SecurityRequirement(name = "Bear Authentication")
    @DeleteMapping("/{job_id}")
    public ResponseEntity<String> deleteJobById(@PathVariable String job_id) {
        jobService.deleteJobById(job_id);
        logger.info("Deleted job with ID: {}", job_id);

        return new ResponseEntity<>("Job entity was deleted successfully", HttpStatus.OK);
    }

     */

}
