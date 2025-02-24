package com.nishintGoyal.SecurityApp.SecurityApplication2025.dtos;

import jakarta.persistence.Column;

public class UserDto
{
    private Long id;

    private String name;

    private String email;



    public UserDto()
    {

    }
    public UserDto(Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
