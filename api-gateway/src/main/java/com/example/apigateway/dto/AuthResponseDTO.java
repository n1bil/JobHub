package com.example.apigateway.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponseDTO {

//    private String id;
//    private String name;
//    private String lastName;
//    private String email;
//    private String location;
    private String accessToken;
}
