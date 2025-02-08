package com.nishintGoyal.SecurityApp.SecurityApplication2025.services;

import com.nishintGoyal.SecurityApp.SecurityApplication2025.dtos.LoginDto;
import com.nishintGoyal.SecurityApp.SecurityApplication2025.entities.UserEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService
{
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public String login(LoginDto loginDto)
    {
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );

        UserEntity userEntity=(UserEntity) authentication.getPrincipal();
        return jwtService.generateToken(userEntity);
    }
}
