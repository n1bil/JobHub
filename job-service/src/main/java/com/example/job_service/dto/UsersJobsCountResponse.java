package com.example.job_service.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersJobsCountResponse {

    private Long users;
    private Long jobs;
}
