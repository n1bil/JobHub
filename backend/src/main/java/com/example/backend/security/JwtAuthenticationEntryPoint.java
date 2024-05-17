package com.example.backend.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    public JwtAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setContentType("application/json");

        Map<String, Object> responseBody = new HashMap<>();

        if (authException instanceof BadCredentialsException) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            responseBody.put("message", "Invalid username or password");
        } else if (authException instanceof LockedException) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            responseBody.put("message", "Account is locked");
        } else if (authException instanceof DisabledException) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            responseBody.put("message", "Account is disabled");
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            responseBody.put("message", "Access Denied");
        }

        objectMapper.writeValue(response.getWriter(), responseBody);
    }
}
