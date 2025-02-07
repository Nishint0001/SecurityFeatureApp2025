package com.nishintGoyal.SecurityApp.SecurityApplication2025.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
public class WebServiceConfig
{
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
    {
        httpSecurity
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/posts").permitAll()//public for everyone
                        .requestMatchers("/posts/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults());

        return httpSecurity.build();
    }

    //MAKING USER'S

    @Bean
    UserDetailsService myInMemoryUserDetailsService()
    {
        //making users
        UserDetails normalUser= User
                .withUsername("Nishint")
                .password(passwordEncoder().encode("12345"))
                .roles("User")
                .build();

        UserDetails adminUser=User
                .withUsername("ADMIN")
                .password(passwordEncoder().encode("54321"))
                .roles("ADMIN")
                .build();

        // registering them
        return new InMemoryUserDetailsManager(normalUser,adminUser);

    }

    //password decrupter
    @Bean
    PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    //copied from chatgpt to authenticate them

    @Bean
    public AuthenticationManager authenticationManager(
            @Qualifier("myInMemoryUserDetailsService") UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(List.of(authProvider));
    }
}
