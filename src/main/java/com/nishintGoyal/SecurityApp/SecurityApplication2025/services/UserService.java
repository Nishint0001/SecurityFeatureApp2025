package com.nishintGoyal.SecurityApp.SecurityApplication2025.services;

import com.nishintGoyal.SecurityApp.SecurityApplication2025.entities.UserEntity;
import com.nishintGoyal.SecurityApp.SecurityApplication2025.exceptions.ResourceNotFoundException;
import com.nishintGoyal.SecurityApp.SecurityApplication2025.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService
{
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        UserEntity userEntity=userRepository.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("user with email not foundd"+username));
        return userEntity;
    }

}
