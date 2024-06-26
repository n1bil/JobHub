package com.example.backend.mapper;

import com.example.backend.dto.jobDTO.JobCreateRequestDTO;
import com.example.backend.dto.jobDTO.Jobs;
import com.example.backend.entity.Job;

public class JobMapper {

    public Job mapToJob(JobCreateRequestDTO jobRequestDTO) {
        return Job.builder()
                .company(jobRequestDTO.getCompany())
                .position(jobRequestDTO.getPosition())
                .jobStatus(jobRequestDTO.getJobStatus())
                .jobType(jobRequestDTO.getJobType())
                .jobLocation(jobRequestDTO.getJobLocation())
                .build();
    }

    public Jobs mapToJobResponseDTO(Job job) {
        return Jobs.builder()
                .id(job.getId())
                .company(job.getCompany())
                .jobLocation(job.getJobLocation())
                .jobType(job.getJobType())
                .jobStatus(job.getJobStatus())
                .position(job.getPosition())
                .createdBy(job.getCreatedBy().getId())
                .createdAt(job.getCreatedAt())
                .updatedAt(job.getUpdatedAt())
                .build();
    }

}
