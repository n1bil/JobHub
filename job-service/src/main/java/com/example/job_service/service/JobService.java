package com.example.job_service.service;

import com.example.job_service.dto.jobDTO.JobCreateRequestDTO;
import com.example.job_service.dto.jobDTO.JobResponseDTO;
import com.example.job_service.dto.jobDTO.Jobs;

public interface JobService {

    Jobs createJob(JobCreateRequestDTO jobRequestDTO);

    JobResponseDTO getAllJobsByUser(String search, String jobStatus, String jobType, String sort, int page, int limit);

//    Jobs getJob(String jobId);
//
//    Jobs updateJobById(JobUpdateRequestDTO requestJob, String jobId);
//
//    void deleteJobById(String jobId);

}
