package com.nishintGoyal.SecurityApp.SecurityApplication2025.services;

import com.nishintGoyal.SecurityApp.SecurityApplication2025.dtos.LoginDto;
import com.nishintGoyal.SecurityApp.SecurityApplication2025.dtos.LoginResponseDto;
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
    private final UserService userService;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService, UserService userService)
    {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    public LoginResponseDto login(LoginDto loginDto)
    {
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );

        UserEntity userEntity=(UserEntity) authentication.getPrincipal();
        String accessToken=jwtService.generateAccessToken(userEntity);
        String refreshToken= jwtService.generateRefreshAccessToken(userEntity);

        return new LoginResponseDto(userEntity.getId(),accessToken,refreshToken);

    }

    public LoginResponseDto refreshToken(String refreshToken)
    {

        Long userId=jwtService.getUserIdFromToken(refreshToken);

        UserEntity userEntity=userService.getUserById(userId);

        String accessToken=jwtService.generateAccessToken(userEntity);

        return new LoginResponseDto(userEntity.getId(),accessToken,refreshToken);




    }
}
