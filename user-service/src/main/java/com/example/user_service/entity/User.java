package com.example.user_service.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class User {

    @Id
    private String id;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private String location;
    private String role;
    private String avatar;
    private String avatarPublicId;

}
