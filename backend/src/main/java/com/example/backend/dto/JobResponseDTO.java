package com.example.backend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobResponseDTO {

    private String id;
    private String company;
    private String position;
    private String jobStatus;
    private String jobType;
    private String jobLocation;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
