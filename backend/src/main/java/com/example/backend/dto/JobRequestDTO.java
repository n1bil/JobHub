package com.example.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobRequestDTO {

    @NotBlank(message = "Company must be filled")
    private String company;
    @NotBlank(message = "Position must be filled")
    private String position;
    private String jobStatus = "pending";
    private String jobType = "full-time";
    private String jobLocation = "my-city";

}
