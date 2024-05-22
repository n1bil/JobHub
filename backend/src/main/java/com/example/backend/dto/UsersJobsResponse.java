package com.example.backend.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersJobsResponse implements Serializable {

    private Long users;
    private Long jobs;
}
