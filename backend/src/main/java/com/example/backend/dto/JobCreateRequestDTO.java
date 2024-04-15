package com.example.backend.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobCreateRequestDTO {

    @NotEmpty(message = "Company name is required")
    private String company;
    @NotEmpty(message = "Position title is required")
    private String position;
    private String jobStatus = "pending";
    private String jobType = "full-time";
    private String jobLocation = "my-city";

}
