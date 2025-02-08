package com.nishintGoyal.SecurityApp.SecurityApplication2025.filters;

import com.nishintGoyal.SecurityApp.SecurityApplication2025.entities.UserEntity;
import com.nishintGoyal.SecurityApp.SecurityApplication2025.services.JwtService;
import com.nishintGoyal.SecurityApp.SecurityApplication2025.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter
{
    private final JwtService jwtService;
    private final UserService userService;

    public JwtAuthFilter(JwtService jwtService, UserService userService)
    {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
    final String requestTokenHeader=request.getHeader("Authorization");

    //Bearer se start hota hai token

    if(requestTokenHeader==null || requestTokenHeader.startsWith("Bearer ")==false)
    {
        filterChain.doFilter(request, response);
        return;
    }

//    String token=requestTokenHeader.split("Bearer")[1];
    String token = requestTokenHeader.substring(7).trim(); // Extract and trim the token

// like token kuch aisa hoga "Bearer sgsgnngs.jgsjs.kgngjj"
        //toh kevel sgsgnngs.jgsjs.kgngjj yeh aa jyega token me

        Long userId=jwtService.getUserIdFromToken(token);

        if(userId!=null && SecurityContextHolder.getContext().getAuthentication()==null)
        {
            UserEntity userEntity=userService.getUserById(userId);
            UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userEntity,null,null);

            authenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );//for adding ip address in token

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }
}
