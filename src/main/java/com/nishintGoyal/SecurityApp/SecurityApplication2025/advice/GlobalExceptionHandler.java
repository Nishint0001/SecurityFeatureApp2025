package com.nishintGoyal.SecurityApp.SecurityApplication2025.advice;


import com.nishintGoyal.SecurityApp.SecurityApplication2025.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException e)
    {
        ApiError apiError=new ApiError(e.getLocalizedMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiError,HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler(AuthenticationException.class)
//    public ResponseEntity<ApiError> handleAuthenticationException(AuthenticationException e)
//    {
//        ApiError apiError=new ApiError(e.getLocalizedMessage(), HttpStatus.UNAUTHORIZED);
//        return new ResponseEntity<>(apiError,HttpStatus.UNAUTHORIZED);
//    }
@ExceptionHandler(BadCredentialsException.class)
public ResponseEntity<ApiError> handleBadCredentialsException(BadCredentialsException e)
{
    ApiError apiError = new ApiError("Invalid username or password", HttpStatus.UNAUTHORIZED);
    return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
}

}
