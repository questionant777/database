package ru.otus.spring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.domain.JwtRequest;
import ru.otus.spring.domain.JwtResponse;
import ru.otus.spring.service.JwtAuthService;

@Controller
public class JwtAuthController {
    private final JwtAuthService jwtAuthService;

    public JwtAuthController(JwtAuthService jwtAuthService) {
        this.jwtAuthService = jwtAuthService;
    }

    @PostMapping("/auth")
    public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        return ResponseEntity.ok(jwtAuthService.createAuthenticationToken(authenticationRequest));
    }
}
