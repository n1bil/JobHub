package com.example.backend.controller;

import com.example.backend.dto.JobRequestDTO;
import com.example.backend.dto.JobResponseDTO;
import com.example.backend.entity.Job;
import com.example.backend.service.JobService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<List<Job>> getJobs() {
        logger.info("Retrieved {} jobs", jobs.size());

        return ResponseEntity.ok(jobs);
    }

    @PostMapping()
    public ResponseEntity<JobResponseDTO> addJob(@RequestBody JobRequestDTO jobRequest) {
        JobResponseDTO createdJob = jobService.createJob(jobRequest);

        return new ResponseEntity<>(createdJob, HttpStatus.CREATED);
    }

    @GetMapping("/{job_id}")
    public ResponseEntity<?> getJobById(@PathVariable String job_id) {
        Optional<Job> optionalJob = jobs.stream()
                .filter(job -> job.getId().equals(job_id))
                .findFirst();

        if (optionalJob.isPresent()) {
            Job job = optionalJob.get();
            logger.info("Retrieved job with id {}: {}", job_id, job);
            return ResponseEntity.ok(job);
        } else {
            return ResponseEntity.notFound().build();
        }
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
