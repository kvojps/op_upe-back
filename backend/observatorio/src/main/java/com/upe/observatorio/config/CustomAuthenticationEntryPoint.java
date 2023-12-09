package com.upe.observatorio.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        HttpStatus statusCode = HttpStatus.FORBIDDEN;

        if (authException instanceof InsufficientAuthenticationException) {
            statusCode = HttpStatus.UNAUTHORIZED;
        }

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(statusCode.value());

        Map<String, Object> message = new HashMap<>();
        message.put("mesage: ", "User " + statusCode.name().toLowerCase());
        response.getWriter().write(
                MAPPER.writeValueAsString(message)
        );
    }
}
