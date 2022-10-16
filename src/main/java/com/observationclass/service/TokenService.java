package com.observationclass.service;

import com.observationclass.model.AuthResponse;
import com.observationclass.model.LoginRequest;
import com.observationclass.security.jwt.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;
    /*public AuthResponse login(LoginRequest loginRequest) throws Exception{
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtTokenUtils.generateJwtToken(authenticate);
        return new AuthResponse(token);
    }*/
}
