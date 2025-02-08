package com.nishintGoyal.SecurityApp.SecurityApplication2025.services;

import com.nishintGoyal.SecurityApp.SecurityApplication2025.dtos.LoginDto;
import com.nishintGoyal.SecurityApp.SecurityApplication2025.dtos.SignUpDto;
import com.nishintGoyal.SecurityApp.SecurityApplication2025.dtos.UserDto;
import com.nishintGoyal.SecurityApp.SecurityApplication2025.entities.UserEntity;
import com.nishintGoyal.SecurityApp.SecurityApplication2025.exceptions.ResourceNotFoundException;
import com.nishintGoyal.SecurityApp.SecurityApplication2025.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService
{
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        UserEntity userEntity=userRepository.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("user with email not foundd"+username));
        return userEntity;
    }

    public UserDto signUp(SignUpDto signUpDto)
    {
        Optional<UserEntity> userEntity=userRepository.findByEmail(signUpDto.getEmail());

        if(userEntity.isPresent())
        {
            throw new BadCredentialsException("User with this email already exist"+signUpDto.getEmail());
        }

        UserEntity convertToEntity=modelMapper.map(signUpDto, UserEntity.class);
        convertToEntity.setPassword(passwordEncoder.encode(convertToEntity.getPassword()));

        UserEntity savedEntity=userRepository.save(convertToEntity);
        UserDto convertBackToDto=modelMapper.map(savedEntity,UserDto.class);

        return convertBackToDto;
    }

}
