package com.example.backend.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "jobs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class Job {

    @Id
    private String id;
    private String company;
    private String position;
    private String jobStatus;
    private String jobType;
    private String jobLocation;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public void setJobStatus(String jobStatus) {
        if (isValidJobStatus(jobStatus)) {
            this.jobStatus = jobStatus;
        } else {
            throw new IllegalArgumentException("Invalid job status: " + jobStatus);
        }
    }

    private boolean isValidJobStatus(String status) {
        return status.equals("interview") || status.equals("declined") || status.equals("pending");
    }

    public void setJobType(String jobType) {
        if (isValidJobType(jobType)) {
            this.jobType = jobType;
        } else {
            throw new IllegalArgumentException("Invalid job type: " + jobType);
        }
    }

    private boolean isValidJobType(String type) {
        return type.equals("full-time") || type.equals("part-time") || type.equals("internship");
    }
}
