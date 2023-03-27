package ru.otus.spring.service;

import org.springframework.security.core.Authentication;
import ru.otus.spring.domain.JwtRequest;
import ru.otus.spring.domain.JwtResponse;

public interface JwtAuthService {
    JwtResponse createAuthenticationToken(JwtRequest authenticationRequest) throws Exception;
    Authentication authenticate(String username, String password) throws Exception;
}
