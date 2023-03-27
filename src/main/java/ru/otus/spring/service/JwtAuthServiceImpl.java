package ru.otus.spring.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.JwtRequest;
import ru.otus.spring.domain.JwtResponse;

@Service
public class JwtAuthServiceImpl implements JwtAuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    public JwtAuthServiceImpl(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public JwtResponse createAuthenticationToken(JwtRequest authenticationRequest) {
        Authentication auth = authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetailsAuth = (UserDetails) auth.getPrincipal();

        final String token = jwtTokenUtil.generateToken(userDetailsAuth);

        return new JwtResponse(token);
    }

    public Authentication authenticate(String username, String password) {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
