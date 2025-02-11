package com.nishintGoyal.SecurityApp.SecurityApplication2025.config;

import com.nishintGoyal.SecurityApp.SecurityApplication2025.filters.JwtAuthFilter;
import com.nishintGoyal.SecurityApp.SecurityApplication2025.handlers.OAuth2SuccessHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class WebServiceConfig
{
    private final JwtAuthFilter jwtAuthFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    public WebServiceConfig(JwtAuthFilter jwtAuthFilter, OAuth2SuccessHandler oAuth2SuccessHandler)
    {
        this.jwtAuthFilter = jwtAuthFilter;
        this.oAuth2SuccessHandler = oAuth2SuccessHandler;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
    {
        httpSecurity
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/posts","/error","/auth/**","/home.html").permitAll()//public for everyone
                        .anyRequest().authenticated())
                .csrf(csrfConfig->csrfConfig.disable())
                .sessionManagement(sessionConfig->sessionConfig
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth2config->oauth2config
                        .failureUrl("/login?error=true")
                        .successHandler(oAuth2SuccessHandler));


                //.formLogin(Customizer.withDefaults());

        return httpSecurity.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception
    {
        return configuration.getAuthenticationManager();
    }

    //MAKING USER'S

//    @Bean
//    UserDetailsService myInMemoryUserDetailsService()
//    {
//        //making users
//        UserDetails normalUser= User
//                .withUsername("Nishint")
//                .password(passwordEncoder().encode("12345"))
//                .roles("User")
//                .build();
//
//        UserDetails adminUser=User
//                .withUsername("ADMIN")
//                .password(passwordEncoder().encode("54321"))
//                .roles("ADMIN")
//                .build();
//
//        // registering them
//        return new InMemoryUserDetailsManager(normalUser,adminUser);
//
//    }

//    //password descriptor


//
//    //copied from chatgpt to authenticate them
//
//    @Bean
//    public AuthenticationManager authenticationManager(
//            @Qualifier("myInMemoryUserDetailsService") UserDetailsService userDetailsService,
//            PasswordEncoder passwordEncoder) {
//
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService);
//        authProvider.setPasswordEncoder(passwordEncoder);
//
//        return new ProviderManager(List.of(authProvider));
//    }
}
