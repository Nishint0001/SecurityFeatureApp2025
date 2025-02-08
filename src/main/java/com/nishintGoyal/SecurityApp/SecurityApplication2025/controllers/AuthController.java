package com.nishintGoyal.SecurityApp.SecurityApplication2025.controllers;


import com.nishintGoyal.SecurityApp.SecurityApplication2025.dtos.LoginDto;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
    {
        String token=authService.login(loginDto);

        Cookie cookie=new Cookie("CookieToken", token);
        cookie.setHttpOnly(true);
        httpServletResponse.addCookie(cookie);


        return ResponseEntity.ok(token);
    }
}
