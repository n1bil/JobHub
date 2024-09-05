package com.example.apigateway.service;

import com.example.apigateway.dto.AuthRequestDTO;
import com.example.apigateway.dto.AuthResponseDTO;
import com.example.apigateway.dto.RegisterRequestDTO;
import com.example.apigateway.entity.User;
import com.example.apigateway.dto.RegisterResponseDTO;
import com.example.apigateway.mapper.UserMapper;
import com.example.apigateway.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessTokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import javax.ws.rs.core.Response;

import java.nio.file.AccessDeniedException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final AuthRepository authRepository;
    private final UserMapper userMapper;
    private final Keycloak keycloak;

    @Override
    public RegisterResponseDTO registerUser(RegisterRequestDTO userRequestDTO) {
        logger.info("Attempting to register user with email: {}", userRequestDTO.getEmail());

        RealmResource realmResource = keycloak.realm("spring-boot-microservices-realm");
        UsersResource usersResource = realmResource.users();

        UserRepresentation user = new UserRepresentation();
        user.setUsername(userRequestDTO.getEmail());
        user.setEmail(userRequestDTO.getEmail());
        user.setEnabled(true);

        Response response = usersResource.create(user);
        if (response.getStatus() != 201) {
            logger.error("User registration failed with status: {}", response.getStatus());
            throw new RuntimeException("User registration failed");
        }

        String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(userRequestDTO.getPassword());

        usersResource.get(userId).resetPassword(passwordCred);

        User newUser = userMapper.mapToUser(userRequestDTO);
        authRepository.save(newUser);

        logger.info("User successfully registered with email: {}", userRequestDTO.getEmail());
        return userMapper.mapToResponseDTO(newUser);
    }

    @Override
    public AuthResponseDTO loginUser(AuthRequestDTO loginDto) {
        try {
            logger.info("Attempting to log in user with email: {}", loginDto.getEmail());

            Keycloak keycloak = KeycloakBuilder.builder()
                    .serverUrl("http://localhost:8080/api/v1/auth")
                    .realm("spring-boot-microservices-realm")
                    .clientId("spring-cloud-client")
                    .clientSecret("0rD2JxF4TolUl6Pwc7BxkU1vog68bK93")
                    .username(loginDto.getEmail())
                    .password(loginDto.getPassword())
                    .grantType(OAuth2Constants.PASSWORD)
                    .build();

            AccessTokenResponse tokenResponse = keycloak.tokenManager().getAccessToken();

            AuthResponseDTO authResponseDTO = new AuthResponseDTO();
            authResponseDTO.setAccessToken(tokenResponse.getToken());

            logger.info("User successfully logged in with email: {}", loginDto.getEmail());
            return authResponseDTO;

        } catch (Exception e) {
            logger.error("Login failed for user with email: {}", loginDto.getEmail(), e);
            throw new RuntimeException("Login failed", e);
        }
    }

    @Override
    public RegisterResponseDTO getCurrentUser() {
        User user = getCurrentAuthUser();

        return userMapper.mapToResponseDTO(user);
    }

    public User getCurrentAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("User is not authenticated");
        }
        String email = authentication.getName();


        try {
            return authRepository.findByEmail(email)
                    .orElseThrow(() -> new AccessDeniedException("Access denied"));
        } catch (AccessDeniedException e) {
            throw new RuntimeException(e);
        }
    }
}
