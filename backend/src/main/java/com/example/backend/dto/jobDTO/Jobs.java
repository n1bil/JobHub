package com.example.backend.dto.jobDTO;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Jobs {

    private String id;
    private String company;
    private String position;
    private String jobStatus;
    private String jobType;
    private String jobLocation;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
