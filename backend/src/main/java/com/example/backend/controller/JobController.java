package com.example.backend.controller;

import com.example.backend.dto.JobRequestDTO;
import com.example.backend.dto.JobResponseDTO;
import com.example.backend.entity.Job;
import com.example.backend.service.JobService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {
    private static final Logger logger = LoggerFactory.getLogger(JobController.class);
    private List<Job> jobs = new ArrayList<>();

    private JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping()
    public ResponseEntity<List<JobResponseDTO>> getJobs() {
        List<JobResponseDTO> allJobs = jobService.getAllJobs();
        logger.info("All jobs: {}", allJobs.size());

        return ResponseEntity.ok(allJobs);
    }

    @PostMapping()
    public ResponseEntity<JobResponseDTO> addJob(@Valid @RequestBody JobRequestDTO jobRequest) {
        JobResponseDTO createdJob = jobService.createJob(jobRequest);
        logger.info("Job created: {}", createdJob);

        return new ResponseEntity<>(createdJob, HttpStatus.CREATED);
    }

    @GetMapping("/{job_id}")
    public ResponseEntity<JobResponseDTO> getJobById(@PathVariable String job_id) {
        JobResponseDTO receivedJob = jobService.getJob(job_id);

        logger.info("Received Job: {}", receivedJob);
        return ResponseEntity.ok(receivedJob);
    }

    @PutMapping("/{job_id}")
    public ResponseEntity<?> editJobById(@RequestBody Job requestJob,
                                         @PathVariable String job_id) {
        Optional<Job> optionalJob = jobs.stream()
                .filter(job -> job.getId().equals(job_id))
                .findFirst();

        if (optionalJob.isPresent()) {
            Job jobToUpdate = optionalJob.get();
            jobToUpdate.setCompany(requestJob.getCompany());
            jobToUpdate.setPosition(requestJob.getPosition());
            return ResponseEntity.ok(jobToUpdate);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{job_id}")
    public ResponseEntity<String> deleteJobById(@PathVariable String job_id) {
        Optional<Job> optionalJob = jobs.stream()
                .filter(job -> job.getId().equals(job_id))
                .findFirst();

        if (optionalJob.isPresent()) {
            jobs.remove(optionalJob.get());
            return ResponseEntity.ok("Job with id " + job_id + " successfully deleted");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
