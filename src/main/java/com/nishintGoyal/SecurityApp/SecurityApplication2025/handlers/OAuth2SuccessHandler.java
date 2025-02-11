package com.nishintGoyal.SecurityApp.SecurityApplication2025.handlers;

import com.nishintGoyal.SecurityApp.SecurityApplication2025.entities.UserEntity;
import com.nishintGoyal.SecurityApp.SecurityApplication2025.services.JwtService;
import com.nishintGoyal.SecurityApp.SecurityApplication2025.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;

@Component

public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler
{
    private static final Logger log = LoggerFactory.getLogger(OAuth2SuccessHandler.class);

    private final UserService userService;
    private final JwtService jwtService;

    public OAuth2SuccessHandler(UserService userService, JwtService jwtService)
    {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException
    {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) token.getPrincipal();

        String email=oAuth2User.getAttribute("email");

        UserEntity userEntity=userService.getUserByEmail(email);

        if(userEntity==null)
        {
            UserEntity newUser=UserEntity.builder()
                    .name(oAuth2User.getAttribute("name"))
                    .email(email)
                    .build();

            userEntity=userService.saveUser(newUser);

        }

        String accessToken=jwtService.generateAccessToken(userEntity);
        String refreshToken= jwtService.generateRefreshAccessToken(userEntity);

        Cookie cookie=new Cookie("refreshToken",refreshToken);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        //   String frontendUrl="http://localhost:8080/home.html?token="+accessToken;
        String frontendUrl = "/home.html?token=" + accessToken;

       // getRedirectStrategy().sendRedirect(request, response, frontendUrl);

        response.sendRedirect(frontendUrl);

    }
}
