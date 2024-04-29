package com.example.backend.controller;

import com.example.backend.dto.jobDTO.JobCreateRequestDTO;
import com.example.backend.dto.jobDTO.JobResponseDTO;
import com.example.backend.dto.jobDTO.Jobs;
import com.example.backend.dto.jobDTO.JobUpdateRequestDTO;
import com.example.backend.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {
    private static final Logger logger = LoggerFactory.getLogger(JobController.class);
    private JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @Operation(summary = "Get all jobs", description = "Endpoint to retrieve all jobs")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved jobs")
    @SecurityRequirement(name = "Bear Authentication")
    @GetMapping()
    public ResponseEntity<JobResponseDTO> getJobs(@RequestParam(value = "search", required = false) String search,
                                                  @RequestParam(value = "jobStatus", required = false) String jobStatus,
                                                  @RequestParam(value = "jobType", required = false) String jobType,
                                                  @RequestParam(defaultValue = "newest") String sort,
                                                  @RequestParam(defaultValue = "1") int page,
                                                  @RequestParam(defaultValue = "10") int limit) {
        JobResponseDTO allJobs = jobService.getAllJobsByUser(search, jobStatus, jobType, sort, page, limit);
        logger.info("Retrieved all jobs. Total count: {}", allJobs.getJobs().size());

        return ResponseEntity.ok(allJobs);
    }

    @Operation(summary = "Create a new job", description = "Create a new job with the provided details")
    @ApiResponse(responseCode = "201", description = "Job created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @SecurityRequirement(name = "Bear Authentication")
    @PostMapping()
    public ResponseEntity<Jobs> addJob(@Valid @RequestBody JobCreateRequestDTO jobRequest) {
        Jobs createdJob = jobService.createJob(jobRequest);
        logger.info("Created job with ID: {}", createdJob.getId());

        return new ResponseEntity<>(createdJob, HttpStatus.CREATED);
    }

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
    @PreAuthorize("hasRole('role')")
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
    @PreAuthorize("hasRole('role')")
    @DeleteMapping("/{job_id}")
    public ResponseEntity<String> deleteJobById(@PathVariable String job_id) {
        jobService.deleteJobById(job_id);
        logger.info("Deleted job with ID: {}", job_id);

        return new ResponseEntity<>("Job entity was deleted successfully", HttpStatus.OK);
    }

}
