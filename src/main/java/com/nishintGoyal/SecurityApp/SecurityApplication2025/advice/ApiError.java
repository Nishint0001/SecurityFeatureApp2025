package com.nishintGoyal.SecurityApp.SecurityApplication2025.advice;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiError
{
    private LocalDateTime timeStamp;
    private String error;
    private HttpStatus statusCode;

    public ApiError()
  {
      this.timeStamp=LocalDateTime.now();
  }
  public ApiError(String error,HttpStatus statusCode)
  {
      this();
      this.error=error;
      this.statusCode=statusCode;
  }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }
}
