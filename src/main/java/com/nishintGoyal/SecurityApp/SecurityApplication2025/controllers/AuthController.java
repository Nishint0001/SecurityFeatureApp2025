package com.nishintGoyal.SecurityApp.SecurityApplication2025.controllers;


import com.nishintGoyal.SecurityApp.SecurityApplication2025.dtos.LoginDto;
import com.nishintGoyal.SecurityApp.SecurityApplication2025.dtos.LoginResponseDto;
import com.nishintGoyal.SecurityApp.SecurityApplication2025.dtos.SignUpDto;
import com.nishintGoyal.SecurityApp.SecurityApplication2025.dtos.UserDto;
import com.nishintGoyal.SecurityApp.SecurityApplication2025.services.AuthService;
import com.nishintGoyal.SecurityApp.SecurityApplication2025.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.Arrays;

@RestController
@RequestMapping("/auth")
public class AuthController
{
    private final UserService userService;
    private final AuthService authService;

    public AuthController(UserService userService, AuthService authService)
    {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/signUp")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpDto signUpDto)
    {
        UserDto userDto=userService.signUp(signUpDto);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
    {
        LoginResponseDto loginResponseDto=authService.login(loginDto);

        Cookie cookie=new Cookie("refreshToken",loginResponseDto.getRefreshToken());
        cookie.setHttpOnly(true);
        httpServletResponse.addCookie(cookie);

        return ResponseEntity.ok(loginResponseDto);
    }
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refresh(HttpServletRequest request)
    {

        String refreshToken=Arrays.stream(request.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(cookie -> cookie.getValue())
                .orElseThrow(()->new AuthenticationServiceException("Refresh token not found inside cookie"));

        LoginResponseDto loginResponseDto=authService.refreshToken(refreshToken);

        return ResponseEntity.ok(loginResponseDto);

    }
}
